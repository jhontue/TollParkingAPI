package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Bill;
import com.jhontue.parking.api.PricingPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Pricing policy for each hour spent in the parking (nb hours * hour price).
 * Based on BigDecimal with a scale 2 and rounding mode HALF_UP.
 */
public class PerHourPricingPolicy implements PricingPolicy {

    /**
     * The hour price
     */
    private final BigDecimal hourPrice;

    /**
     *  Constructor
     * @param hourPrice the hour price
     */
    public PerHourPricingPolicy(BigDecimal hourPrice) {
        this.hourPrice = hourPrice.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal computePrice(LocalDateTime arrivalTime, LocalDateTime departureTime) {
        Duration duration = Duration.between(departureTime, arrivalTime);
        long durationAsHours = duration.toHours();
        BigDecimal hoursRounded = new BigDecimal(durationAsHours).setScale(2, RoundingMode.HALF_UP);
        BigDecimal amount = hoursRounded.multiply(hourPrice);
        return amount;
    }
}
