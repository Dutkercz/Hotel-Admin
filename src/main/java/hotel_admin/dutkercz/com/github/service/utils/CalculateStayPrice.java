package hotel_admin.dutkercz.com.github.service.utils;

import hotel_admin.dutkercz.com.github.model.Stay;

import java.math.BigDecimal;

public class CalculateStayPrice {

    public static BigDecimal calculateStayPrice(Stay stay) {
        var countPeople = stay.getGuests().size() + 1;

        if (countPeople == 1){
            return BigDecimal.valueOf(140.0 * stay.getStayAmount());
        } else if (countPeople == 2) {
            return BigDecimal.valueOf(240.0 * stay.getStayAmount());
        } else if (countPeople == 3) {
            return BigDecimal.valueOf(350.0 * stay.getStayAmount());
        }else {
            return BigDecimal.valueOf(400.0 * stay.getStayAmount());
        }
    }
}
