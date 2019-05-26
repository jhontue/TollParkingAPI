package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ParkingSlotService {

    /**
     *
     */
    private final List<ParkingSlotUtilization> slots;

    /**
     * @param slots
     */
    public ParkingSlotService(List<ParkingSlotUtilization> slots) {
        this.slots = slots;
    }

    /**
     * @param car
     */
    public Optional<ParkingSlotUtilization> checkin(Car car) {
        // TODO check inputs

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
     * @param parkingSlot
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
