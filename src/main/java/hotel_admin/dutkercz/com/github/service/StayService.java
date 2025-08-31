package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.repository.ClientRepository;
import hotel_admin.dutkercz.com.github.repository.RoomRepository;
import hotel_admin.dutkercz.com.github.repository.StayRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        room.setStatus(RoomStatusEnum.OCCUPIED);

        Client client = clientService.findByid(stay.getClient().getId());

        stay.setCheckIn(LocalDateTime.now());
        stay.setRoom(room);
        stay.setClient(client);
        stayRepository.save(stay);
    }
}
