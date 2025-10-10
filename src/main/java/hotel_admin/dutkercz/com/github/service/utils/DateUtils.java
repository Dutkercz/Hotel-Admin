package hotel_admin.dutkercz.com.github.service.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DateUtils {

    public static LocalDate defineActualLocalDate(LocalDateTime now){
        return now.toLocalTime().isBefore(LocalTime.NOON) ?
                LocalDate.now().minusDays(1)
                :
                LocalDate.now();
    }

    public static LocalDateTime defineActualLocalDateTime(LocalDateTime now){
        return (now.toLocalTime().isBefore(LocalTime.NOON) ?
                        LocalDate.now().minusDays(1)
                        :
                        LocalDate.now()).atTime(12, 1);
    }

    public static LocalDateTime defineTodayDateToCheckOut(){
        return (LocalDateTime.now().toLocalTime().isAfter(LocalTime.NOON) ?
                        LocalDate.now().plusDays(1).atTime(8,0)
                        :
                        LocalDateTime.now());
    }

    public static List<Integer> getDaysOfMonth() {
        return  IntStream.rangeClosed(1, YearMonth.now().lengthOfMonth()).boxed().toList();
    }

    public static String formatMonth() {
        int yearMonth = YearMonth.now().getMonthValue();
        return yearMonth < 10 ? "0" + yearMonth : String.valueOf(yearMonth);
    }
}
