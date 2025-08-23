package hotel_admin.dutkercz.com.github.controller;

import com.sun.jdi.LongValue;
import hotel_admin.dutkercz.com.github.dtos.ClientUpdate;
import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/new")
    public String showForm (Model model){
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") Client client){
        clientService.saveClient(client);
        return "redirect:/clients/list";
    }

    @GetMapping("/list")
    public String listClients(Model model){
        model.addAttribute("clients", clientService.findAllClients());
        return "client-list";
    }

    @GetMapping("/detail")
    public String findByCpf(@RequestParam("cpf") String cpf, Model model){
        model.addAttribute("clientFound", clientService.findByCpf(cpf));
        return "client-detail";
    }

    @GetMapping("/update/{id}")
    public String clientFormUpdate(@PathVariable Long id, Model model){
        model.addAttribute("client", clientService.findByid(id));
        return "client-update";
    }

    @PostMapping("/update/{id}")
    public String clientUpdate(@PathVariable Long id, @Valid @ModelAttribute ClientUpdate clientUpdate){
        clientService.updateClient(id, clientUpdate);
        return "redirect:/";
    }
}
