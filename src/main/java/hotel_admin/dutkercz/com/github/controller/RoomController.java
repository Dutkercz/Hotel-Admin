package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.service.RoomSevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomSevice roomSevice;

    public RoomController(RoomSevice roomSevice) {
        this.roomSevice = roomSevice;
    }

    @GetMapping("/list")
    public String findAll(@ModelAttribute Model model){
        model.addAttribute("rooms", roomSevice.findAll());
        return "rooms";
    }

    @GetMapping("/set-maintenance/{id}")
    public String changeMaintenanceStatus(@PathVariable Long id){
        roomSevice.setStatus(Room.
                )

    }
}
