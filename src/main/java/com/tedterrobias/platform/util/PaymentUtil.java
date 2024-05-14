package com.tedterrobias.platform.util;

import com.tedterrobias.platform.enumeration.PricingEnum;
import com.tedterrobias.platform.enumeration.VehicleEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PaymentUtil {
    public static BigDecimal calculateParkingPayment(LocalDateTime startParkTime, LocalDateTime endParkTime,
                                                     VehicleEnum vehicleType)
    {
        int minutes = (int) ChronoUnit.MINUTES.between(startParkTime, endParkTime);
        int hours = minutes / 60;
        int excessMinutes = minutes % 60;
        BigDecimal totalAmount = new BigDecimal(0);

        if((hours < 3) || (hours == 3 && excessMinutes == 0))
        {
            if (vehicleType.equals(VehicleEnum.TWO_WHEELED)) {
                totalAmount = BigDecimal.valueOf(PricingEnum.TWO_WHEELED_INITIAL_RATE.getValue());
            } else if (vehicleType.equals(VehicleEnum.FOUR_WHEELED)) {
                totalAmount = BigDecimal.valueOf(PricingEnum.FOUR_WHEELED_INITIAL_RATE.getValue());
            }
        } else {
            int excessHours = hours - PricingEnum.INITIAL_HOURS.getValue();

            if (vehicleType.equals(VehicleEnum.TWO_WHEELED)) {
                totalAmount = BigDecimal.valueOf(
                        PricingEnum.TWO_WHEELED_INITIAL_RATE.getValue() +
                                (int) (excessHours * PricingEnum.TWO_WHEELED_SUCCEEDING_RATE.getValue()) +
                                (excessMinutes > 0 ? PricingEnum.TWO_WHEELED_SUCCEEDING_RATE.getValue() : 0));
            } else {
                totalAmount = BigDecimal.valueOf(
                        PricingEnum.FOUR_WHEELED_INITIAL_RATE.getValue() +
                                (int) (excessHours * PricingEnum.FOUR_WHEELED_SUCCEEDING_RATE.getValue()) +
                                (excessMinutes > 0 ? PricingEnum.FOUR_WHEELED_SUCCEEDING_RATE.getValue() : 0));
            }
        }
        return totalAmount;
    }
}
