package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    boolean existsByStartMaintenanceBetween(LocalDateTime todayDate, LocalDateTime now);

    List<Maintenance> findAllByStartMaintenanceBetween(LocalDateTime todayDate, LocalDateTime localDateTime);

    List<Maintenance> findAllByIsActiveTrue();
}
