package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.service.ClientService;
import hotel_admin.dutkercz.com.github.service.RoomSevice;
import hotel_admin.dutkercz.com.github.service.StayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stays")
public class StayController {

    private final StayService stayService;
    private final RoomSevice roomSevice;
    private final ClientService clientService;

    public StayController(StayService stayService, RoomSevice roomSevice, ClientService clientService) {
        this.stayService = stayService;
        this.roomSevice = roomSevice;
        this.clientService = clientService;
    }

    @GetMapping("/new")
    public String showStayForm(@RequestParam("roomId") Long roomId, Model model){
        Room room = roomSevice.findById(roomId);
        Stay stay = new Stay();
        stay.setRoom(room);
        model.addAttribute("stay", stay);
        model.addAttribute("clients", clientService.findAllActive());
        return "stay-form";
    }

    @PostMapping("/save")
    public String saveStay(@ModelAttribute("stay") Stay stay){
        stayService.saveStay(stay);
        return "redirect:/";
    }
}
