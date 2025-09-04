package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Long> {
    List<Stay> findAllByClientId(Long id);

    List<Stay> findAllByClientCpf(String formattedCpf);
}
