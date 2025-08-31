package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/list")
    public String findAll(@ModelAttribute Model model){
        model.addAttribute("rooms", roomService.findAll());
        return "rooms";
    }

    @GetMapping("/set-maintenance/{id}")
    public String changeMaintenanceStatus(@PathVariable Long id){
        roomService.setMaintenanceStatus(id);
        return "redirect:/";
    }
}
