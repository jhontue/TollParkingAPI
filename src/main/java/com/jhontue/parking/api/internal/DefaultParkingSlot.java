package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;

/**
 * Default implementation of a parking slot. It has a car type and an unique identifier.
 */
public class DefaultParkingSlot implements ParkingSlot {

    /**
     * A car type
     */
    private final Car.CarType carType;

    /**
     * The parking slot unique identifier
     */
    private final String id;

    /**
     * Constructor
     *
     * @param carType the car type of the parking slot
     * @param id      the unique identifier
     */
    public DefaultParkingSlot(Car.CarType carType, String id) {
        this.carType = carType;
        this.id = id;
    }

    @Override
    public Car.CarType getCarType() {
        return carType;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultParkingSlot{");
        sb.append("carType=").append(carType);
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
