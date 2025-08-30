package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
}
