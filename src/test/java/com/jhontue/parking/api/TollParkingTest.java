package com.jhontue.parking.api;

import com.jhontue.parking.api.Car.CarType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test the class {@link TollParking} which is the entry point of the TollParkingAPI
 */
public class TollParkingTest {

    /**
     * Check main requirement for checkin :
     * - Send them to the right parking slot of refuse them if there is no slot (of the right type) left.
     */
    @Test
    public void checkinCar() {
        // create parking with one gasoline slot
        TollParking parking = TollParking.create()
                .addParkingSlot(CarType.GASOLINE, 1)
                .perHourPricing(new BigDecimal(2.00))
                .build();

        // first gasoline car gets a slot
        Optional<ParkingSlot> parkingSlot = parking.checkinCar(Car.of(CarType.GASOLINE));

        assertTrue(parkingSlot.isPresent());
        assertEquals(CarType.GASOLINE, parkingSlot.get().getCarType());

        // check no more slot are available for gasoline or electric
        Optional<ParkingSlot> secondParkingSlot = parking.checkinCar(Car.of(CarType.GASOLINE));
        Optional<ParkingSlot> electricParkingSlot = parking.checkinCar(Car.of(CarType.ELECTRIC_20KW));

        assertFalse(secondParkingSlot.isPresent());
        assertFalse(electricParkingSlot.isPresent());
    }

    /**
     * Check main requirement for checkout :
     * - Mark the parking slot as Free when the car leaves it.
     * - Bill the customer when the car leaves.
     *
     */
    @Test
    public void checkoutCar() {

        // create parking with one gasoline slot
        TollParking parking = TollParking.create()
                .addParkingSlot(CarType.GASOLINE, 1)
                .perHourPricing(new BigDecimal(2.00))
                .build();

        // first gasoline car gets a slot
        Optional<ParkingSlot> firstParkingSlot = parking.checkinCar(Car.of(CarType.GASOLINE));

        // check bill is returned
        Bill bill = parking.checkoutCar(firstParkingSlot.get());

        assertNotNull(bill);
        assertNotNull(bill.getAmount());
        assertNotNull(bill.getArrivalTime());
        assertNotNull(bill.getDepartureTime());

    }
}