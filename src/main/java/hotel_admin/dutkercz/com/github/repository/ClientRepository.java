package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByCpfAndStatus(String replaced, ClientStatusEnum active);
}
