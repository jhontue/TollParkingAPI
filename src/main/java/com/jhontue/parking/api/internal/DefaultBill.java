package com.jhontue.parking.api.internal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DefaultBill implements com.jhontue.parking.api.Bill {

    private BigDecimal amount;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    public DefaultBill amount(BigDecimal amount){
        this.amount = amount;
        return this;
    }

    public DefaultBill arrivalTime(LocalDateTime arrivedTime){
        this.arrivalTime = arrivedTime;
        return this;
    }

    public DefaultBill departureTime(LocalDateTime departureTime){
        this.departureTime = departureTime;
        return this;
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
