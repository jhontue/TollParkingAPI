package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Bill;
import com.jhontue.parking.api.PricingPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Pricing policy for a fixed amount + each hour spent in the parking (fixed amount + nb hours * hour price)
 * The fixed amount scale is 2 and and rounding mode HALF_UP.
 */
public class PerHourAndFixedAmountPricingPolicy implements PricingPolicy {

    /**
     * Pricing for each hour
     */
    private final PerHourPricingPolicy perHourPricingPolicy;

    /**
     * Fixed amount to add
     */
    private final BigDecimal fixedAmount;

    /**
     * Constructor
     *
     * @param hourPrice   hour price
     * @param fixedAmount fixed amount
     */
    public PerHourAndFixedAmountPricingPolicy(BigDecimal hourPrice, BigDecimal fixedAmount) {
        this.perHourPricingPolicy = new PerHourPricingPolicy(hourPrice);
        this.fixedAmount = fixedAmount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal computePrice(LocalDateTime arrivalTime, LocalDateTime departureTime) {
        BigDecimal perHourPrice = this.perHourPricingPolicy.computePrice(arrivalTime, departureTime);
        return perHourPrice.add(this.fixedAmount);
    }
}
