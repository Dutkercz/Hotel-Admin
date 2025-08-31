package hotel_admin.dutkercz.com.github.service;

import hotel_admin.dutkercz.com.github.dtos.ClientUpdate;
import hotel_admin.dutkercz.com.github.mapstruct.ClientMapper;
import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum;
import hotel_admin.dutkercz.com.github.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum.ACTIVE;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public void saveClient(Client client){
        client.setStatus(ACTIVE);
        clientRepository.save(client);
    }

    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client findByCpf(String cpf) {
        var replaced = cpf.replaceAll("[.,-]", "");
        System.out.println(replaced);
        Client client = clientRepository.findByCpf(replaced)
                .orElseThrow(() -> new EntityNotFoundException("Cliente de CPF +" + replaced + " não encontrado"));
        if (client.getStatus().equals(ClientStatusEnum.BANNED)
                || client.getStatus().equals(ClientStatusEnum.INACTIVE)) {
            return null;
        }
        return client;
    }

    public Client findByid(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente de ID +" + id + " não encontrado"));
    }

    @Transactional
    public void updateClient(Long id, ClientUpdate clientUpdate) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente de ID +" + id + " não encontrado"));
        clientMapper.updateEntityFromDto(clientUpdate, client);
        clientRepository.save(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente de ID +" + id + " não encontrado"));
        clientRepository.inactiveClient(client.getId(), ClientStatusEnum.INACTIVE);
    }

    public List<Client> findAllActive() {
        return clientRepository.findAllByStatus(ACTIVE);
    }
}
