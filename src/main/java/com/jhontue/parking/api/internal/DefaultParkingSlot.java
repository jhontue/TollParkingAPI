package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;

public class DefaultParkingSlot implements ParkingSlot {

    private final Car.CarType carType;
    private final String id;

    /**
     * Constructor
     * @param carType
     * @param id
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
}
