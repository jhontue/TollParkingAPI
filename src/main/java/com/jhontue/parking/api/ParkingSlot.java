package com.jhontue.parking.api;

import com.jhontue.parking.api.Car.CarType;
import com.jhontue.parking.api.internal.DefaultParkingSlot;

/**
 * Represents a parking slot. A parking slot can only contain a car with the same car type.
 */
public interface ParkingSlot {

    /**
     * The car type compatible with the parking slot
     *
     * @return the car type
     */
    CarType getCarType();

    /**
     * An unique identifier for the parking slot.
     *
     * @return the parking slot id
     */
    String getId();

    /**
     * Factory method to obtain a default parking slot
     *
     * @param carType the compatible car type
     * @param id      the parking slot id
     * @return a parking slot
     */
    static ParkingSlot of(CarType carType, String id) {
        return new DefaultParkingSlot(carType, id);
    }

}
