package com.jhontue.parking.api;

import com.jhontue.parking.api.internal.DefaultBill;
import com.jhontue.parking.api.internal.ParkingSlotService;
import com.jhontue.parking.api.internal.ParkingSlotUtilization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class TollParking {
    /**
     *
     */
    private PricingPolicy pricingPolicy;

    /**
     *
     */
    private ParkingSlotService parkingSlotService;

    /**
     * Private constructor
     */
    private TollParking(){
    }

    /**
     * @param car
     * @return
     */
    public Optional<ParkingSlot> checkinCar(Car car) {
        Optional<ParkingSlotUtilization> optionalSlotUtilization = parkingSlotService.checkin(car);
        return optionalSlotUtilization.map(ParkingSlotUtilization::getParkingSlot);
    }

    /**
     * @param parkingSlot
     * @return a bill
     */
    public Bill checkoutCar(ParkingSlot parkingSlot) {
        Optional<ParkingSlotUtilization> optSlotUtilization = parkingSlotService.checkout(parkingSlot);

        // throw exception if no slot is found
        if (optSlotUtilization.isEmpty()) {
            throw new IllegalArgumentException("no parking "); // TODO
        }

        // compute bill with the pricing policy
        ParkingSlotUtilization slotUtilization = optSlotUtilization.get();
        BigDecimal amount = pricingPolicy.computePrice(slotUtilization.getArrivalTime(), slotUtilization.getDepartureTime());

        Bill bill = new DefaultBill()
                .amount(amount)
                .arrivalTime(slotUtilization.getArrivalTime())
                .departureTime(slotUtilization.getDepartureTime());
        return bill;
    }

    /**
     *
     */
    public static class TollParkingBuilder {

        private PricingPolicy pricingPolicy;
        private List<ParkingSlotUtilization> slotUtilizationList = new ArrayList<>();

        /**
         *
         * @param pricingPolicy
         * @return
         */
        public TollParkingBuilder pricingPolicy(PricingPolicy pricingPolicy) {
            this.pricingPolicy = pricingPolicy;
            return this;
        }

        /**
         *
         * @param carType
         * @param numberOfSlots
         * @return
         */
        public TollParkingBuilder addParkingSlot(Car.CarType carType, int numberOfSlots) {
            List<ParkingSlotUtilization> newSlots = IntStream
                    .range(0, numberOfSlots)
                    .mapToObj(i -> new ParkingSlotUtilization(ParkingSlot.of(carType, UUID.randomUUID().toString())))
                    .collect(Collectors.toList());
            slotUtilizationList.addAll(newSlots);
            return this;
        }

        public TollParking build() {
            // default values
            return null;
        }
    }

}
