package hotel_admin.dutkercz.com.github.repository;

import hotel_admin.dutkercz.com.github.model.Room;
import hotel_admin.dutkercz.com.github.model.enums.StayStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
    SELECT DISTINCT r
    FROM Room r
    LEFT JOIN FETCH r.stays s
    WHERE s.status = :status OR s.id IS NULL
    """)
    List<Room> findAllWithActiveStaysOrEmpty(@Param("status") StayStatusEnum status);
}
