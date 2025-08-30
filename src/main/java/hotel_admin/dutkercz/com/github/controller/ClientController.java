package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.dtos.ClientUpdate;
import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public String showClientForm(Model model){
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") Client client){
        clientService.saveClient(client);
        return "redirect:/clients/list";
    }

    @GetMapping("/list")
    public String listClients(Pageable pageable, Model model){
        Page<Client> clients = clientService.findAllClients(pageable);
        model.addAttribute("clients", clients);
        return "client-list";
    }

    @GetMapping("/find-client")
    public String findByCpfPage(){
        return "client-find";
    }

    @GetMapping("/find-client/update/{cpf}")
    public String findByCpf(@PathVariable("cpf") String cpf){
        var client = clientService.findByCpf(cpf);
        if (client == null) return "redirect:/clients/find-client";
        return "redirect:/clients/update/" + client.getId();
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> clientDelete(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
