package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Client;
import hotel_admin.dutkercz.com.github.model.enums.ClientStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByCpfAndStatus(String replaced, ClientStatusEnum active);

    @Modifying
    @Query("""
            UPDATE Client c
            SET c.status = :inactive
            WHERE c.id = :id
            """)
    void inactiveClient(@Param("id") Long id, @Param("inactive") ClientStatusEnum inactive);

    List<Client> findAllByStatus(ClientStatusEnum active);

    Optional<Client> findByCpf(String replaced);
}
