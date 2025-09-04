package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum;
import hotel_admin.dutkercz.com.github.repository.ClientRepository;
import hotel_admin.dutkercz.com.github.repository.RoomRepository;
import hotel_admin.dutkercz.com.github.repository.StayRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StayService {

    private final StayRepository stayRepository;
    private final RoomService roomService;
    private final ClientService clientService;


    public StayService(StayRepository stayRepository, RoomRepository roomRepository, ClientRepository clientRepository, RoomService roomService, ClientService clientService) {
        this.stayRepository = stayRepository;
        this.roomService = roomService;
        this.clientService = clientService;
    }


    @Transactional
    public void saveStay(Stay stay) {
        Room room = roomService.findById(stay.getRoom().getId());
        Client client = clientService.findByid(stay.getClient().getId());

        room.setStatus(RoomStatusEnum.OCCUPIED);
        stay.setStatus(StayStatusEnum.ACTIVE);
        stay.setStayPrice(calculateStayPrice(stay));
        stay.setCheckIn(LocalDateTime.now());
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
        stay.setStayPrice(calculateStayPrice(stay));
    }

    public List<Stay> findAllByClient(String cpf) {
        var formattedCpf = cpf.replaceAll("[,.-]", "");
        return stayRepository.findAllByClientCpf(formattedCpf);
    }
}
