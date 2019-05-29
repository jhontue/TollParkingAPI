package com.jhontue.parking.api.internal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Default implementation of a bill with builder methods.
 */
public class DefaultBill implements com.jhontue.parking.api.Bill {

    /**
     * The bill amount
     */
    private BigDecimal amount;

    /**
     * The arrival time of the car
     */
    private LocalDateTime arrivalTime;

    /**
     * The departure time of the car
     */
    private LocalDateTime departureTime;

    /**
     * Sets the amount
     * @param amount the bill amount
     * @return the bill instance
     */
    public DefaultBill amount(BigDecimal amount){
        this.amount = amount;
        return this;
    }

    /**
     * Sets the arrival time
     * @param arrivalTime the arrival time of the car
     * @return the bill instance
     */
    public DefaultBill arrivalTime(LocalDateTime arrivalTime){
        this.arrivalTime = arrivalTime;
        return this;
    }

    /**
     * Sets the departure time
     * @param departureTime the departure time of the car
     * @return the bill instance
     */
    public DefaultBill departureTime(LocalDateTime departureTime){
        this.departureTime = departureTime;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultBill{");
        sb.append("amount=").append(amount);
        sb.append(", arrivalTime=").append(arrivalTime);
        sb.append(", departureTime=").append(departureTime);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }


}
