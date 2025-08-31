package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {
    private final RoomService roomService;

    public MainPageController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String mainPage(Model model){
        model.addAttribute("rooms", roomService.findAll());
        return "main-page";
    }
}
