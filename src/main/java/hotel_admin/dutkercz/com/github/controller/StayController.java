package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.exceptions.RoomConflictException;
import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.service.ClientService;
import hotel_admin.dutkercz.com.github.service.RoomService;
import hotel_admin.dutkercz.com.github.service.StayService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Validated
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
        if (room.getStatus().equals(RoomStatusEnum.OCCUPIED) || room.getStatus().equals(RoomStatusEnum.MAINTENANCE)){
            throw new RoomConflictException("Está acomodação não está disponível para checkin");
        }
        Stay stay = new Stay();
        stay.setRoom(room);
        model.addAttribute("stay", stay);
        return "stay-form";
    }

    @PostMapping("/save")
    public String saveStay(@ModelAttribute("stay") Stay stay){
        if(stay.getGuests() != null){
            stay.getGuests().forEach(x -> x.setStay(stay));
        }
        stay.getGuests().forEach(System.out::println);
        stayService.saveStay(stay);
        return "redirect:/";
    }

    @GetMapping("/checkin/find-client/{cpf}")
    public String findClientForCheckin(@PathVariable("cpf") @NotBlank String cpf,
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

    @GetMapping("/add-stay-amount/{amount}")
    public String addStayAmount(@PathVariable("amount") Integer amount, @RequestParam Long roomId){
        stayService.addStay(amount, roomId);
        return "redirect:/";
    }

    @GetMapping("/client-history/{cpf}")
    public String listClientStays(@PathVariable("cpf") @NotBlank(message = "Digite um cpf válido")
                                  @Pattern(regexp = "\\d{11}", message = "CPF fora do padrão de 11 dígitos")
                                  String cpf,
                                  Model model){
        model.addAttribute("client", clientService.findByCpf(cpf));
        model.addAttribute("clientStays", stayService.findAllByClient(cpf));
        return "client-stay_history";
    }

    @GetMapping("/client-history")
    public String listClientStaysPage(Model model){
        model.addAttribute("clientStays", List.of());
        return "client-stay_history";
    }

}
