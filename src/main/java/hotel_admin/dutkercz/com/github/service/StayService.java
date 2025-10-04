package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.exceptions.RoomConflictException;
import hotel_admin.dutkercz.com.github.model.*;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.repository.StayRepository;
import hotel_admin.dutkercz.com.github.service.utils.CalculateStayExtras;
import hotel_admin.dutkercz.com.github.service.utils.CalculateStayPrice;
import hotel_admin.dutkercz.com.github.service.utils.DateUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum.ACTIVE;
import static hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum.FINISHED;
import static hotel_admin.dutkercz.com.github.service.utils.CalculateStayPrice.calculateStayPrice;

@Service
public class StayService {

    private final StayRepository stayRepository;
    private final RoomService roomService;
    private final ClientService clientService;
    private final MaintenanceService maintenanceService;
    private final ExtrasService extrasService;


    public StayService(StayRepository stayRepository, RoomService roomService, ClientService clientService, MaintenanceService maintenanceService, ExtrasService extrasService) {
        this.stayRepository = stayRepository;
        this.roomService = roomService;
        this.clientService = clientService;
        this.maintenanceService = maintenanceService;
        this.extrasService = extrasService;
    }


    @Transactional
    public void saveStay(Stay stay) {
        Room room = roomService.findById(stay.getRoom().getId());
        Client client = clientService.findByid(stay.getClient().getId());

        room.setStatus(RoomStatusEnum.OCCUPIED);
        stay.setStatus(ACTIVE);
        stay.setStayPrice(calculateStayPrice(stay));
        stay.setCheckIn(LocalDateTime.now());
        stay.setCheckOut(stay.getCheckIn(), stay.getStayAmount());
        stay.setRoom(room);
        stay.setClient(client);
        stayRepository.save(stay);
    }


    @Transactional
    public void addStay(Integer amount, Long roomId) {
        Room room = roomService.findById(roomId);
        Stay stay = room.getCurrentStay();
        stay.setStayAmount(stay.getStayAmount() + amount);
        stay.setCheckOut(stay.getCheckIn(), stay.getStayAmount());
        stay.setStayPrice(calculateStayPrice(stay));
    }

    public List<Stay> findAllByClient(String cpf) {
        var formattedCpf = cpf.replaceAll("[,.-]", "");
        return stayRepository.findAllByClientCpf(formattedCpf);
    }

    public List<Stay> findByActualMont(YearMonth actualMonth) {
        return stayRepository.findAllByCheckInBetween(actualMonth.atDay(1).atStartOfDay(), actualMonth.atEndOfMonth().atTime(12, 0, 0));
    }

    public Stay findByRoomIdAndActive(Long roomId) {
        return stayRepository.findByRoomIdAndStatus(roomId, ACTIVE)
                .orElseThrow(() -> new EntityNotFoundException("Diária não encontrada"));
    }

    public Room newStaySetRoom(Long roomId) {
        Room room = roomService.findById(roomId);
        if (room.getStatus().equals(RoomStatusEnum.OCCUPIED) || room.getStatus().equals(RoomStatusEnum.MAINTENANCE)){
            throw new RoomConflictException("Está acomodação não está disponível para checkin");
        }
        return room;
    }

    public Client newStaySetClient(@NotBlank String cpf) {
        Client client = clientService.findByCpf(cpf.replaceAll("[,.-]", ""));
        if (client == null){
            throw new EntityNotFoundException("Cliente não encontrado");
        }
        return client;
    }

    public Map<String, Map<Long, String>> createStatusRoomMap() {
        YearMonth actualMonth = YearMonth.now();
        List<Integer> days = DateUtils.getDaysOfMonth();

        List<Room> rooms = roomService.findAll();
        List<Stay> stays = stayRepository.findAllByCheckInBetween(actualMonth.atDay(1).atStartOfDay(), actualMonth.atEndOfMonth().atTime(12, 0, 0));;
        List<Maintenance> maintenances = maintenanceService.findAllMonthMaintenance();
        Map<String, Map<Long, String>> statusMap = new HashMap<>();

        for (Integer referenceDay : days) {
            Map<Long, String> dayMap = new HashMap<>();
            LocalDateTime actualDayOfMonth = actualMonth.atDay(referenceDay).atTime(12, 1);

            for (Room r : rooms) {
                var status = "AVAILABLE";

                for (Maintenance m : maintenances) {
                    if (m.getRoom().getId().equals(r.getId())
                            && !actualDayOfMonth.isBefore(m.getStartMaintenance()) //só vale se o dia for igual ou depois do dia atual que estamos olhando
                            && (m.getEndMaintenance() == null || !actualDayOfMonth.isAfter(m.getEndMaintenance()))
                    ) {
                        status = "MAINTENANCE";
                        break;
                    }
                }
                if (status.equals("AVAILABLE")) {
                    for (Stay s : stays) {
                        if (s.getRoom().getId().equals(r.getId())
                                && !actualDayOfMonth.isBefore(s.getCheckIn())
                                && !actualDayOfMonth.isAfter(s.getCheckOut())) {
                            status = "OCCUPIED";
                            break;
                        }
                    }
                }

                dayMap.put(r.getId(), status);
            }
            statusMap.put(referenceDay.toString(), dayMap);
        }
        return statusMap;
    }

    @Transactional
    public void saveCheckOut(Long stayId, Extras extras) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow(() -> new EntityNotFoundException("Hospedagem não encontrada"));

        extras.setStay(stay);
        extrasService.saveExtras(extras);
        roomService.checkOutRoom(stay.getRoom());
        BigDecimal total = CalculateStayExtras.calculateStayExtras(calculateStayPrice(stay), extras);
        stay.setStayPrice(total);
        stay.setStatus(FINISHED);

        stayRepository.save(stay);
    }

}
