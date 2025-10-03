package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.model.Extras;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.service.RoomService;
import hotel_admin.dutkercz.com.github.service.StayService;
import hotel_admin.dutkercz.com.github.service.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Validated
@RequestMapping("/stays")
public class StayController {

    private final StayService stayService;
    private final RoomService roomService;

    public StayController(StayService stayService, RoomService roomService) {
        this.stayService = stayService;
        this.roomService = roomService;
    }

    @GetMapping("/new")
    public String showStayForm(@RequestParam("roomId") Long roomId, Model model){
        Stay stay = new Stay();
        stay.setRoom(stayService.newStaySetRoom(roomId));

        model.addAttribute("date", LocalDate.now());
        model.addAttribute("stay", stay);
        return "stay-form";
    }

    @PostMapping("/save")
    public String saveStay(@ModelAttribute("stay") Stay stay){
        if(stay.getGuests() != null){
            stay.getGuests().forEach(x -> x.setStay(stay));
        }
        stayService.saveStay(stay);
        return "redirect:/";
    }

    @GetMapping("/checkin/find-client/{cpf}")
    public String findClientForCheckin(@PathVariable("cpf") @NotBlank String cpf,
                                       @RequestParam Long roomId, // mantém a referencia ao apto escolhido
                                       Model model) {
        Stay stay = new Stay();
        stay.setRoom(stayService.newStaySetRoom(roomId));
        stay.setClient(stayService.newStaySetClient(cpf));
        model.addAttribute("stay", stay);
        model.addAttribute("date", LocalDate.now());
        return "stay-form";
    }

    @GetMapping("/add-stay-amount")
    public String addStayAmount(@RequestParam Long roomId){
        stayService.addStay(1, roomId);
        return "redirect:/";
    }

    @GetMapping("/monthly-grid")
    public String monthlyGrid(@RequestParam(name = "filtro", required = false) String filtro, Model model){
        Map<String, Map<Long, String>> statusMap = stayService.createStatusRoomMap();
        String actualMonthFormatted = DateUtils.formatMonth();

        int actualYear = YearMonth.now().getYear();

        int today = DateUtils.defineActualLocalDate(LocalDateTime.now()).getDayOfMonth();
        List<Integer> days = DateUtils.getDaysOfMonth();
        if ("hoje".equals(filtro)) {
            days = days.stream().filter(d -> d >= today).collect(Collectors.toList());
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("today", today);

        model.addAttribute("actualMonth", actualMonthFormatted);
        model.addAttribute("actualYear", actualYear);
        model.addAttribute("days", days);
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("statusMap", statusMap);
        return "monthly-grid";
    }

    @GetMapping("/checkout")
    public String checkOut(@RequestParam Long roomId, Model model){
        Stay stay = stayService.findByRoomIdAndActive(roomId);
        model.addAttribute("stay", stay );
        model.addAttribute("extras", new Extras());
        return "stay-checkout";
    }

    @PostMapping("/checkout/save/{stayId}")
    public String checkOutSave(@PathVariable("stayId") Long stayId, @ModelAttribute("extras") Extras extras, RedirectAttributes redirectAttributes){
        stayService.saveCheckOut(stayId, extras);

        redirectAttributes.addFlashAttribute("success", "Checkout realizado com sucesso!!");
        return "redirect:/";
    }
}
