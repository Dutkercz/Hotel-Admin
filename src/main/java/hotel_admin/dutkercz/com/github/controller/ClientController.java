package hotel_admin.dutkercz.com.github.controller;

import hotel_admin.dutkercz.com.github.dtos.ClientUpdate;
import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum;
import hotel_admin.dutkercz.com.github.service.ClientService;
import hotel_admin.dutkercz.com.github.service.StayService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Validated
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //entrega o formulario de cadastro
    @GetMapping("/new")
    public String showClientForm(Model model){
        model.addAttribute("client", new Client());
        return "client-form";
    }

    //salva a cliente preenchida no formulario
    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") Client client, RedirectAttributes redirectAttributes){
        clientService.saveClient(client);
        redirectAttributes.addFlashAttribute("success", "Cliente cadastrado com sucesso.");
        return "redirect:/";
    }

    //entrega uma lista de clientes
    @GetMapping("/list")
    public String listClients(Pageable pageable, Model model){
        Page<Client> clients = clientService.findAllClients(pageable);
        model.addAttribute("clients", clients);
        return "client-list";
    }

    //entrega a pagina de busca por cpf
    @GetMapping("/find-client")
    public String findByCpfPage(){
        return "client-find";
    }

    //redireciona para a pagina de update já com o cliente carregado se for válido
    @GetMapping("/find-client/update/{cpf}")
    public String findByCpf(@PathVariable("cpf") @NotBlank String cpf){
        var client = clientService.findByCpf(cpf);
        return "redirect:/clients/update/" + client.getId();
    }

    @GetMapping("/update/{id}")
    public String clientFormUpdate(@PathVariable Long id, Model model){
        model.addAttribute("client", clientService.findByid(id));
        return "client-update";
    }

    @PostMapping("/update/{id}")
    public String clientUpdate(@PathVariable Long id, @Valid @ModelAttribute ClientUpdate clientUpdate, RedirectAttributes redirectAttributes){
        clientService.updateClient(id, clientUpdate);
        redirectAttributes.addFlashAttribute("success" ,"Cliente atualizado com sucesso");
        return "redirect:/clients/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> clientDelete(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client-history")
    public String listClientStaysPage(Model model){
        model.addAttribute("clientStays", List.of());
        return "client-stay_history";
    }

    @GetMapping("/client-history/{cpf}")
    public String listClientStays(@PathVariable("cpf") @NotBlank(message = "Digite um cpf válido")
                                  @Pattern(regexp = "\\d{11}", message = "CPF fora do padrão de 11 dígitos")
                                  String cpf,
                                  Model model){
        model.addAttribute("client", clientService.findByCpf(cpf));
        model.addAttribute("clientStays", clientService.findAllStaysByCpf(cpf));
        return "client-stay_history";
    }

    @GetMapping("/detail")
    public String clientDetail(@RequestParam("cpf") String cpf,Model model, RedirectAttributes redirectAttributes){
        Client client = clientService.findByCpf(cpf);
        model.addAttribute("client", client);
        return "client-detail";
    }
}
