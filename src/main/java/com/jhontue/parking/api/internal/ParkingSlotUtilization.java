package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the parking slot utilization by a car. Il keeps time arrival and departure.
 */
public class ParkingSlotUtilization {

    /**
     * The parking slot
     */
    private final ParkingSlot parkingSlot;

    /**
     * The car using the parking slot
     */
    private Car car;

    /**
     * The arrival time
     */
    private LocalDateTime arrivalTime;

    /**
     * The departure time
     */
    private LocalDateTime departureTime;

    /**
     * Constructor
     *
     * @param parkingSlot the parking slot that will be managed
     */
    public ParkingSlotUtilization(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    /**
     * Determines if a car is compatible with this parking slot
     *
     * @param car a car
     * @return <code>true</code> if the car has the same car type as the parking slot, <code>false</code> otherwise
     */
    public boolean canReceive(Car car) {
        return this.parkingSlot.getCarType().equals(car.getCarType());
    }

    /**
     * Determines if the parking slot is fre (no car using it).
     *
     * @return <code>true</code> if the parking slot is free, <code>false</code> otherwise
     */
    public boolean isFree() {
        return Objects.isNull(this.car);
    }

    /**
     * Gets the parking slot
     *
     * @return a prking slot
     */
    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    /**
     * Gets the car using the parkin slot
     *
     * @return a car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets a car using the parking slot
     *
     * @param car a car
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Sets the arrival time of the car at the parking slot
     *
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Sets the departure time, when the car leaves the parkin slot
     *
     * @param departureTime the departure time
     */
    public void setDepartureTime(LocalDateTime departureTime) {
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
