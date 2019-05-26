package com.jhontue.parking.api;

import com.jhontue.parking.api.internal.PerHourAndFixedAmountPricingPolicy;
import com.jhontue.parking.api.internal.PerHourPricingPolicy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FunctionalInterface
public interface PricingPolicy {

    /**
     *
     * @param arrivalTime
     * @param departureTime
     * @return
     */
    BigDecimal computePrice(LocalDateTime arrivalTime, LocalDateTime departureTime);

    /**
     *
     * @param hourPrice
     * @return
     */
    static PricingPolicy perHour(BigDecimal hourPrice) {
        return new PerHourPricingPolicy(hourPrice);
    }

    /**
     *
     * @param hourPrice
     * @param fixedAmount
     * @return
     */
    static PricingPolicy perHourAndFixedAmount(BigDecimal hourPrice, BigDecimal fixedAmount) {
        return new PerHourAndFixedAmountPricingPolicy(hourPrice, fixedAmount);
    }

}
