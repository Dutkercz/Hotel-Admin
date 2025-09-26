package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Stay;
import hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StayRepository extends JpaRepository<Stay, Long> {
    List<Stay> findAllByClientId(Long id);

    List<Stay> findAllByClientCpf(String formattedCpf);

    List<Stay> findAllByCheckInBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<Stay> findByRoomIdAndStatus(Long roomId, StayStatusEnum status);
}
