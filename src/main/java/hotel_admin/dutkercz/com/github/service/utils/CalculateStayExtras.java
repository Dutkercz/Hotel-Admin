package hotel_admin.dutkercz.com.github.service.utils;

import hotel_admin.dutkercz.com.github.model.Extras;

import java.math.BigDecimal;

public class CalculateStayExtras {

    public static BigDecimal calculateStayExtras(BigDecimal stayPrice, Extras extras) {
       return
        stayPrice.add(extras.getWaterQuantity() != null && extras.getWaterQuantity() > 0 ?
                        BigDecimal.valueOf(extras.getWaterQuantity() * 4.00) : BigDecimal.ZERO)
                .add(extras.getSodaQuantity() != null  && extras.getSodaQuantity() > 0?
                        BigDecimal.valueOf(extras.getSodaQuantity() * 5.00) : BigDecimal.ZERO)
                .add(extras.getCoffeeQuantity() != null && extras.getCoffeeQuantity() > 0 ?
                        BigDecimal.valueOf(extras.getCoffeeQuantity() * 20.00) : BigDecimal.ZERO);
    }

}
