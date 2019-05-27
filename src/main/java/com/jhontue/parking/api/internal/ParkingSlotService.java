package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;
import com.jhontue.parking.api.ParkingTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The parking slot availability service. Allows to checkin and check out cars into/from the parking slots.
 */
public class ParkingSlotService {

    /**
     * The list of parking slots
     */
    private final List<ParkingSlotUtilization> slots;

    /**
     * Lock is necessary for dealing with concurrency and prevent multiple checkins or checkouts of the same slot.
     * A lock is needed because parallel threads could get the same slot when checkin.
     *  And also, parallel threads could checkout twice the same parking slot.
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Constructor
     *
     * @param slots the list of parking slots
     */
    public ParkingSlotService(List<ParkingSlotUtilization> slots) {
        this.slots = slots;
    }

    /**
     * Allows to checkin a car in the parking. It reserves a parking slot and sets the arrival time. If no parking slot
     * is available for the right type, the checkin is refused, no parking slot will be returned.
     * This operation is thread safe.
     *
     * @param car a car
     * @return a parking slot utilization if available
     * @throws IllegalArgumentException if the car is null
     */
    public Optional<ParkingSlot> checkin(Car car) {
        // check inputs
        if (Objects.isNull(car)) {
            throw new IllegalArgumentException("car must not be null");
        }

        // take the lock before searching
        lock.lock();

        Optional<ParkingSlotUtilization> optSlotUtilization;
        try {
            // get first parking slot of the right type if available
            optSlotUtilization = slots.stream()
                    .filter(slot -> slot.isCompatibleWith(car) && slot.isFree())
                    .findFirst();

            // link slot utilisation and car if available
            optSlotUtilization.ifPresent(
                    slotUtilization -> {
                        slotUtilization.setCar(car);
                        slotUtilization.setArrivalTime(LocalDateTime.now());
                        slotUtilization.setDepartureTime(null);
                    });
        } finally {
            // always unlock
            lock.unlock();
        }


        // return parking slot if available
        return optSlotUtilization.map(ParkingSlotUtilization::getParkingSlot);
    }

    /**
     * Allows to checkout a car from the parking. The parking slot is freed and becomes available for checkin.
     * The departure time is set when the car is leaving.
     * This operation is thread safe.
     *
     * @param parkingSlot the parking slot to be freed
     * @return a parking slot utilization
     * @throws IllegalArgumentException if parking slot is null
     */
    public Optional<ParkingTime> checkout(ParkingSlot parkingSlot) {
        // check input
        if (Objects.isNull(parkingSlot)) {
            throw new IllegalArgumentException("parking slot must not be null");
        }

        // take the lock before searching
        lock.lock();

        Optional<ParkingSlotUtilization> optSlotUtilization;

        try {
            // find parking slot by id
            optSlotUtilization = slots.stream()
                    .filter(slotUtilization -> slotUtilization.getParkingSlot().getId().equals(parkingSlot.getId()) && !slotUtilization.isFree())
                    .findFirst();

            // mark parking slot as free if available
            optSlotUtilization.ifPresent(slotUtilization -> {
                slotUtilization.setCar(null);
                slotUtilization.setDepartureTime(LocalDateTime.now());
            });

        } finally {
            // always unlock
            lock.unlock();
        }

        // return the parking time
        return optSlotUtilization.map(ParkingSlotUtilization::getParkingTime);
    }
}
