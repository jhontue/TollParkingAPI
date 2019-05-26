package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The parking slot availability service. Allows to checkin and check out cars into/from the parking slots.
 */
public class ParkingSlotService {

    /**
     * The list of parking slots
     */
    private final List<ParkingSlotUtilization> slots;

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
     *
     * @param car a car
     * @throw IllegalArgumentException if the car is null
     */
    public Optional<ParkingSlotUtilization> checkin(Car car) {
        // check inputs
        if (Objects.isNull(car)) {
            throw new IllegalArgumentException("car must not be null");
        }

        // get first parking slot of the right type if available
        Optional<ParkingSlotUtilization> optSlotUtilization = slots.stream()
                .filter(slot -> slot.canReceive(car) && slot.isFree())
                .findFirst();

        // link slot utilisation and car if available
        optSlotUtilization.ifPresent(
                slotUtilization -> {
                    slotUtilization.setCar(car);
                    slotUtilization.setArrivalTime(LocalDateTime.now());
                    slotUtilization.setDepartureTime(null);
                });
        return optSlotUtilization;
    }

    /**
     * Allows to checkout a car from the parking. The parking slot is freed and becomes available for checkin.
     * The departure time is set.
     *
     * @param parkingSlot the parking slot to be freed
     */
    public Optional<ParkingSlotUtilization> checkout(ParkingSlot parkingSlot) {
        // TODO check input

        // find parking slot by id
        Optional<ParkingSlotUtilization> optSlotUtilization = slots.stream()
                .filter(slotUtilization -> slotUtilization.getParkingSlot().getId().equals(parkingSlot.getId()) && !slotUtilization.isFree())
                .findFirst();

        // mark parking slot as free if available
        optSlotUtilization.ifPresent(slotUtilization -> {
            slotUtilization.setCar(null);
            slotUtilization.setDepartureTime(LocalDateTime.now());
        });

        return optSlotUtilization;
    }
}
