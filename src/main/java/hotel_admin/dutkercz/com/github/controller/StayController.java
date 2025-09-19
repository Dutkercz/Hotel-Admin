package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.exceptions.RoomConflictException;
import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.Maintenance;
import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.model.enums.RoomStatusEnum;
import hotel_admin.dutkercz.com.github.service.ClientService;
import hotel_admin.dutkercz.com.github.service.MaintenanceService;
import hotel_admin.dutkercz.com.github.service.RoomService;
import hotel_admin.dutkercz.com.github.service.StayService;
import hotel_admin.dutkercz.com.github.service.utils.GetActualDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@Validated
@RequestMapping("/stays")
public class StayController {

    private final StayService stayService;
    private final RoomService roomService;
    private final ClientService clientService;
    private final MaintenanceService maintenanceService;

    public StayController(StayService stayService, RoomService roomService, ClientService clientService, MaintenanceService maintenanceService) {
        this.stayService = stayService;
        this.roomService = roomService;
        this.clientService = clientService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/new")
    public String showStayForm(@RequestParam("roomId") Long roomId, Model model){
        Room room = roomService.findById(roomId);
        if (room.getStatus().equals(RoomStatusEnum.OCCUPIED) || room.getStatus().equals(RoomStatusEnum.MAINTENANCE)){
            throw new RoomConflictException("Está acomodação não está disponível para checkin");
        }
        Stay stay = new Stay();
        stay.setRoom(room);

        model.addAttribute("date", LocalDate.now());
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
        model.addAttribute("date", LocalDate.now());

        return "stay-form";
    }

    @GetMapping("/add-stay-amount")
    public String addStayAmount(@RequestParam Long roomId){
        stayService.addStay(1, roomId);
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

    @GetMapping("/monthly-grid")
    public String monthlyGrid(Model model){
        YearMonth actualMonth = YearMonth.now();
        List<Integer> days = IntStream.rangeClosed(1, actualMonth.lengthOfMonth()).boxed().toList();

        List<Room> rooms = roomService.findAll();
        List<Stay> stays = stayService.findByActualMont(actualMonth);
        List<Maintenance> maintenances = maintenanceService.findAllMonthMaintenance();
        Map<String, Map<Long, String>> statusMap = new HashMap<>();

        // calcula o "hoje" respeitando a regra das diárias
        LocalDate todayDate = GetActualDay.verifyLocalDate(LocalDateTime.now());

        int today = todayDate.getDayOfMonth();
        model.addAttribute("today", today);

        for (Integer referenceDay : days){
            Map<Long, String> dayMap = new HashMap<>();
            LocalDateTime actualDayOfMonth = actualMonth.atDay(referenceDay).atTime(12, 1);

            for (Room r : rooms){
                var status = "AVAILABLE";

                for (Maintenance m : maintenances) {
                    if (m.getRoom().getId().equals(r.getId())
                            && !actualDayOfMonth.isBefore(m.getStartMaintenance()) //só vale se o dia for igual ou depois do dia atual que estamos olhando
                            && (m.getEndMaintenance() == null || !actualDayOfMonth.isAfter(m.getEndMaintenance()))
//ja aqui vamos ver se ainda está em manutenção  /\  e incluir até o final do mes, ou se ja foi finalizada que só vai valer até dia que for igual ou antes do dia atual
                    ) {
                        status = "MAINTENANCE";
                        break;
                    }
                }
                if(status.equals("AVAILABLE")){
                    for (Stay s : stays){
                        if (s.getRoom().getId().equals(r.getId())
                                && !actualDayOfMonth.isBefore(s.getCheckIn())
                                && !actualDayOfMonth.isAfter(s.getCheckOut())) {
                            status = "OCCUPIED";
                            break;
                        }
                    }
                }

                dayMap.put(r.getId(), status);
            }
            statusMap.put(referenceDay.toString(), dayMap);
        }

        String actualMonthFormatted = actualMonth.getMonthValue() < 10 ? "0" + actualMonth.getMonthValue() : String.valueOf(actualMonth.getMonthValue());
        var actualYear = actualMonth.getYear();

        model.addAttribute("actualMonth", actualMonthFormatted);
        model.addAttribute("actualYear", actualYear);
        model.addAttribute("days", days);
        model.addAttribute("rooms", rooms);
        model.addAttribute("statusMap", statusMap);
        return "monthly-grid";
    }

    @GetMapping("/checkout")
    public String checkOut(@RequestParam Long roomId, Model model){
        Stay stay = stayService.findByRoomId(roomId);
        model.addAttribute("stay", stay );
        return "stay-checkout";
    }
}
