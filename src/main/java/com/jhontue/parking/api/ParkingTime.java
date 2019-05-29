package com.jhontue.parking.api;

import java.time.LocalDateTime;

/**
 * Represents a immutable parking time with arrival time and departure time for a parking slot
 */
public class ParkingTime {

    /**
     * The arrival time
     */
    private final LocalDateTime arrivalTime;

    /**
     * The departure time
     */
    private final LocalDateTime departureTime;

    /**
     * Private constructor
     *
     * @param arrivalTime arrival time
     * @param departureTime departure time
     */
    public ParkingTime(LocalDateTime arrivalTime, LocalDateTime departureTime) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    /**
     * Gets the arrival time
     *
     * @return the arrival time
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets the departure time
     *
     * @return the departure time
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
}
