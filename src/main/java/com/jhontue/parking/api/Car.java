package com.jhontue.parking.api;

import com.jhontue.parking.api.internal.DefaultCar;

/**
 * Represents a car with a type.
 */
public interface Car {

    /**
     * The car types. Each parking slot is compatible with a single car type.
     */
    enum CarType {
        GASOLINE,
        ELECTRIC_20KW,
        ELECTRIC_50KW

    }

    /**
     * The type of the car.
     *
     * @return a car type
     */
    CarType getCarType();

    /**
     * Factory method to create a car from its type
     *
     * @param carType a car type
     * @return a car
     */
    static Car of(CarType carType) {
        return new DefaultCar(carType);
    }
}
