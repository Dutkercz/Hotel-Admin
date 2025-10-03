package hotel_admin.dutkercz.com.github.service.utils;

import hotel_admin.dutkercz.com.github.model.Extras;

import java.math.BigDecimal;

public class CalculateStayExtras {

    public static BigDecimal calculateStayExtras(BigDecimal stayPrice, Extras extras) {
        return stayPrice.add(BigDecimal.valueOf(extras.getWaterQuantity() * 4.00))
                .add(BigDecimal.valueOf(extras.getSodaQuantity() * 5.00))
                .add(BigDecimal.valueOf(extras.getCoffeeQuantity() * 20.00));
    }

}
