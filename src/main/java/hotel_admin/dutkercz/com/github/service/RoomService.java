package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.model.Maintenance;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum;
import hotel_admin.dutkercz.com.github.repository.RoomRepository;
import hotel_admin.dutkercz.com.github.service.utils.DateUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room>findAll(){
        return roomRepository.findAll();
    }

    @Transactional
    public void setMaintenanceStatus(Long id) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
        if (room.getStatus().equals(RoomStatusEnum.MAINTENANCE)){
            room.getMaintenances().getLast().setEndMaintenance(DateUtils.defineLocalDateTime(LocalDateTime.now()));
            room.getMaintenances().getLast().setActive(false);
            room.setStatus(RoomStatusEnum.AVAILABLE);
        }else {
            room.setStatus(RoomStatusEnum.MAINTENANCE);
            Maintenance maintenance = new Maintenance();
            maintenance.setStartMaintenance(DateUtils.defineLocalDateTime(LocalDateTime.now()));
            maintenance.setActive(true);
            room.addMaintenance(maintenance);
        }
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
    }

    @Transactional
    public void checkOutRoom(Room room) {
        room.setStatus(RoomStatusEnum.AVAILABLE);
    }
}
