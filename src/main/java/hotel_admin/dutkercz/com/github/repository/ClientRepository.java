package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
