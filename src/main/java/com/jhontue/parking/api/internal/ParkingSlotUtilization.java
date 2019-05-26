package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlotUtilization {

    private final ParkingSlot parkingSlot;
    private Car car;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    /**
     * Constructor
     *
     * @param parkingSlot
     */
    public ParkingSlotUtilization(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public boolean canReceive(Car car) {
        return this.parkingSlot.getCarType().equals(car.getCarType());
    }

    public boolean isFree() {
        return Objects.isNull(this.car);
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
}
