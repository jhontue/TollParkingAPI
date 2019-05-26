package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;

/**
 * Default implementation of a car. The car must have a type.
 */
public class DefaultCar implements Car {

    /**
     * Represents the car type
     */
    private final CarType carType;

    /**
     * Constructor
     *
     * @param carType a car type
     */
    public DefaultCar(CarType carType) {
        this.carType = carType;
    }

    /**
     * Gets the car type
     *
     * @return a car type
     */
    @Override
    public CarType getCarType() {
        return carType;
    }
}
