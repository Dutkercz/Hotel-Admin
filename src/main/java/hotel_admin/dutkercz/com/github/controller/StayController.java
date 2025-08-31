package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.service.ClientService;
import hotel_admin.dutkercz.com.github.service.RoomService;
import hotel_admin.dutkercz.com.github.service.StayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stays")
public class StayController {

    private final StayService stayService;
    private final RoomService roomService;
    private final ClientService clientService;

    public StayController(StayService stayService, RoomService roomService, ClientService clientService) {
        this.stayService = stayService;
        this.roomService = roomService;
        this.clientService = clientService;
    }

    @GetMapping("/new")
    public String showStayForm(@RequestParam("roomId") Long roomId, Model model){
        Room room = roomService.findById(roomId);
        Stay stay = new Stay();
        stay.setRoom(room);
        model.addAttribute("stay", stay);
        return "stay-form";
    }

    @PostMapping("/save")
    public String saveStay(@ModelAttribute("stay") Stay stay){
        stayService.saveStay(stay);
        return "redirect:/";
    }

    @GetMapping("/checkin/find-client/{cpf}")
    public String findClientForCheckin(@PathVariable("cpf") String cpf,
                                       @RequestParam Long roomId, // mantém a referencia ao apto escolhido
                                       Model model) {
        Stay stay = new Stay();
        Room room = roomService.findById(roomId);
        stay.setRoom(room);
        Client client = clientService.findByCpf(cpf);
        stay.setClient(client);
        model.addAttribute("stay", stay);

        return "stay-form";
    }

}
