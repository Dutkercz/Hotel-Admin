package hotel_admin.dutkercz.com.github.service.utils;

import hotel_admin.dutkercz.com.github.model.Stay;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CalculateStayPrice {

    public static BigDecimal calculateStayPrice(Stay stay) {
        System.out.println("Calculate Stay Price Meth");
        var countPeople = stay.getGuests().size() + 1;
        int stayAmountValue = stay.getStayAmount();

        System.out.println("total de diarias "  + stayAmountValue);
        System.out.println("dia do getCheckIn " + stay.getCheckIn().getDayOfMonth());
        System.out.println("Dia do checkout " + stay.getCheckOut());

        if (countPeople == 1){
            return BigDecimal.valueOf(140.0 * stayAmountValue);
        } else if (countPeople == 2) {
            return BigDecimal.valueOf(240.0 * stayAmountValue);
        } else if (countPeople == 3) {
            return BigDecimal.valueOf(350.0 * stayAmountValue);
        }else {
            return BigDecimal.valueOf(400.0 * stayAmountValue);
        }
    }
}
