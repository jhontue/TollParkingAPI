package com.jhontue.parking.api;

import com.jhontue.parking.api.internal.DefaultCar;

/**
 *
 */
public interface Car {

    /**
     *
     */
    enum CarType {
        GASOLINE,
        ELECTRIC_20KW,
        ELECTRIC_50KW

    }

    /**
     *
     * @return
     */
    CarType getCarType();

    /**
     *
     * @param carType
     * @return
     */
    static Car of(CarType carType) {
        return new DefaultCar(carType);
    }
}
