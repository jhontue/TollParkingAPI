package com.jhontue.parking.api;

import com.jhontue.parking.api.internal.PerHourAndFixedAmountPricingPolicy;
import com.jhontue.parking.api.internal.PerHourPricingPolicy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The pricing policy of the parking. It computes the price of the parking time.
 * Two implementations are provided :
 * - {@link PerHourPricingPolicy} : for each hour spent in the parking (nb hours * hour price)
 * - {@link PerHourAndFixedAmountPricingPolicy} : for each hour spent in the parking (nb hours * hour price)
 */
@FunctionalInterface
public interface PricingPolicy {

    /**
     * Provides a price pour the duration of the parking
     *
     * @param arrivalTime   time of arrival
     * @param departureTime time of departure
     * @return the price to be charged to the customer
     */
    BigDecimal computePrice(LocalDateTime arrivalTime, LocalDateTime departureTime);

    /**
     * Factory method for getting a pricing policy per hour
     *
     * @param hourPrice the hour price
     * @return the pricing policy
     */
    static PricingPolicy perHour(BigDecimal hourPrice) {
        return new PerHourPricingPolicy(hourPrice);
    }

    /**
     * Factory method for getting a pricing policy per hour and with a fixed amount
     *
     * @param hourPrice   the hour price
     * @param fixedAmount the fixed amount to be added
     * @return the pricing policy
     */
    static PricingPolicy perHourAndFixedAmount(BigDecimal hourPrice, BigDecimal fixedAmount) {
        return new PerHourAndFixedAmountPricingPolicy(hourPrice, fixedAmount);
    }

}
