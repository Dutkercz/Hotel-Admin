package hotel_admin.dutkercz.com.github.service.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
public class GetActualDay {

    public static LocalDate verifyLocalDate(LocalDateTime now){
        return now.toLocalTime().isBefore(LocalTime.NOON) ?
                LocalDate.now().minusDays(1)
                :
                LocalDate.now();
    }

    public static LocalDateTime verifyLocalDateTime(LocalDateTime now){
        return (now.toLocalTime().isBefore(LocalTime.NOON) ?
                        LocalDate.now().minusDays(1)
                        :
                        LocalDate.now()).atTime(12, 1);
    }
}
