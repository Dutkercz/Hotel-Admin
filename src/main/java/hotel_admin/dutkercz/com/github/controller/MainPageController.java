package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.service.RoomSevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {
    private final RoomSevice roomSevice;

    public MainPageController(RoomSevice roomSevice) {
        this.roomSevice = roomSevice;
    }

    @GetMapping
    public String mainPage(Model model){
        model.addAttribute("rooms", roomSevice.findAll());
        return "main-page";
    }
}
