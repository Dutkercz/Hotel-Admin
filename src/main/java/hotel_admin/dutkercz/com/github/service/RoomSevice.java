package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomSevice {

    private final RoomRepository roomRepository;

    public RoomSevice(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    @Transactional
    public void setMaintenanceStatus(Long id) {
        var room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
        if (room.getStatus().equals(RoomStatusEnum.MAINTENANCE)){
            room.setStatus(RoomStatusEnum.AVAILABLE);
        }else {
            room.setStatus(RoomStatusEnum.MAINTENANCE);
        }
    }
}
