package com.jhontue.parking.api;

import com.jhontue.parking.api.Car.CarType;
import com.jhontue.parking.api.internal.DefaultParkingSlot;

public interface ParkingSlot {

    CarType getCarType();

    String getId();

    /**
     *
     * @param carType
     * @param id
     * @return
     */
    static ParkingSlot of(CarType carType, String id) {
        return new DefaultParkingSlot(carType, id);
    }

}
