package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Bill;
import com.jhontue.parking.api.PricingPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class PerHourAndFixedAmountPricingPolicy implements PricingPolicy {

    private final PerHourPricingPolicy perHourPricingPolicy;
    private final BigDecimal fixedAmount;

    /**
     *
     * @param hourPrice
     * @param fixedAmount
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
