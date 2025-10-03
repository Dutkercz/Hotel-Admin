package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.exceptions.RoomConflictException;
import hotel_admin.dutkercz.com.github.model.*;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.repository.ClientRepository;
import hotel_admin.dutkercz.com.github.repository.RoomRepository;
import hotel_admin.dutkercz.com.github.repository.StayRepository;
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

@Service
public class StayService {

    private final StayRepository stayRepository;
    private final RoomService roomService;
    private final ClientService clientService;
    private final MaintenanceService maintenanceService;


    public StayService(StayRepository stayRepository, RoomRepository roomRepository, ClientRepository clientRepository, RoomService roomService, ClientService clientService, MaintenanceService maintenanceService) {
        this.stayRepository = stayRepository;
        this.roomService = roomService;
        this.clientService = clientService;
        this.maintenanceService = maintenanceService;
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

    private BigDecimal calculateStayPrice(Stay stay) {
        var countPeople = stay.getGuests().size() + 1;

        if (countPeople == 1){
            return BigDecimal.valueOf(140.0 * stay.getStayAmount());
        } else if (countPeople == 2) {
            return BigDecimal.valueOf(240.0 * stay.getStayAmount());
        } else if (countPeople == 3) {
            return BigDecimal.valueOf(350.0 * stay.getStayAmount());
        }else {
            return BigDecimal.valueOf(400.0 * stay.getStayAmount());
        }
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

        BigDecimal total = stay.getStayPrice();
        total = total.add(extras.getCoffeeExtraPrice())
                .add(extras.getSodaExtraPrice())
                .add(extras.getWaterExtraPrice());

        stay.setStayPrice(total);

        System.out.println(total);
        System.out.println(extras.getCoffeeExtraPrice());
        System.out.println(extras.getSodaExtraPrice());
        System.out.println(extras.getWaterExtraPrice());


        stayRepository.save(stay);
    }
}
