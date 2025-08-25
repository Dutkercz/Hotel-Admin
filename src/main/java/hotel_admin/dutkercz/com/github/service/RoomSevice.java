package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.controller.RoomController;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
