package com.jhontue.parking.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents the cost of the parking duration
 */
public interface Bill {

    /**
     * The amount of the bill
     *
     * @return the amount
     */
    BigDecimal getAmount();

    /**
     * The arrival time of the car
     *
     * @return the arrival time
     */
    LocalDateTime getArrivalTime();

    /**
     * The departure time of the car
     *
     * @return the departue time
     */
    LocalDateTime getDepartureTime();
}
