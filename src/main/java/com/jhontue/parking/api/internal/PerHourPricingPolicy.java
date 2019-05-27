package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Bill;
import com.jhontue.parking.api.PricingPolicy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Pricing policy for each hour spent in the parking (nb hours * hour price).
 * Based on BigDecimal with a scale 2 and rounding mode HALF_UP.
 */
public class PerHourPricingPolicy implements PricingPolicy {

    /**
     * The default scale
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * The default rounding mode.
     */
    public static final int DEFAULT_SCALE = 2;

    /**
     * The hour price
     */
    private final BigDecimal hourPrice;

    /**
     *  Constructor
     * @param hourPrice the hour price
     */
    public PerHourPricingPolicy(BigDecimal hourPrice) {
        this.hourPrice = hourPrice.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    @Override
    public BigDecimal computePrice(LocalDateTime arrivalTime, LocalDateTime departureTime) {
        Duration duration = Duration.between(departureTime, arrivalTime);
        long durationAsMinutes = Math.abs(duration.toMinutes());
        BigDecimal nbHours = new BigDecimal(durationAsMinutes).divide(new BigDecimal(60), DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
        BigDecimal amount = nbHours.multiply(hourPrice).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
        return amount;
    }
}
