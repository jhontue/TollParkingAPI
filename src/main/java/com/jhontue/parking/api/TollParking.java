package com.jhontue.parking.api;

import com.jhontue.parking.api.internal.DefaultBill;
import com.jhontue.parking.api.internal.ParkingSlotService;
import com.jhontue.parking.api.internal.ParkingSlotUtilization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * It's the entry point of the TollParkingAPI. The class {@link TollParking} provides management for a toll parking.
 * It handles availability of the parking slots through the checkin/checkout service. A parking slot is configured
 * to accept a single car type, see {@link com.jhontue.parking.api.Car.CarType}.
 * When the checkout is done, a {@link Bill} is calculated according to the princing policy of the parking.
 * A convenient builder is provided to create the parking, see {@link TollParking#create()} to set the
 * proper initialization. A pricing policy is mandatory when creating the parking and the parking slot configuration
 * should be initialized with the builder.
 * Toll parking is thread safe.
 */
public class TollParking {

    /**
     * Logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TollParking.class);

    /**
     * The pricing policy of the parking.
     */
    private PricingPolicy pricingPolicy;

    /**
     * The parking slot availability service.
     */
    private ParkingSlotService parkingSlotService;

    /**
     * Private constructor. Use the builder {@link TollParking#create()} to initialize and create the parking.
     */
    private TollParking() {
    }

    /**
     * Allows to checkin a {@link Car} in the parking if there is a parking slot available of the right type.
     * If there are no slots available, no parking slot is returned.
     *
     * @param car the car entering the parking
     * @return a parking slot if available
     */
    public Optional<ParkingSlot> checkinCar(Car car) {
        LOGGER.info("checkinCar car={}", car);
        Optional<ParkingSlot> optionalParkingSlot = parkingSlotService.checkin(car);
        if (optionalParkingSlot.isPresent()){
            LOGGER.info("parking slot is parkingSlot={} for car={}", optionalParkingSlot.get(), car);
        } else {
            LOGGER.info("no parking slot available for car={}", car);
        }
        return optionalParkingSlot;
    }

    /**
     * Allows to exit the parking slot. If the parking slot is not used or can not be found, an {@link IllegalArgumentException}
     * will be raised.
     * The parking slot is freed and becomes available for another car. A {@link Bill} is provided when the car leaves.
     *
     * @param parkingSlot the parking slot obtained at checkin time
     * @return a bill according to the pricing policy
     *
     * @throws IllegalArgumentException if the parking slot can not be checked out
     */
    public Bill checkoutCar(ParkingSlot parkingSlot) {
        LOGGER.info("checkoutCar from parkingSlot={}", parkingSlot);
        Optional<ParkingTime> optParkingTime = parkingSlotService.checkout(parkingSlot);

        // throw exception if no slot is found
        if (!optParkingTime.isPresent()) {
            throw new IllegalArgumentException("parking slot could not be found or is not used and it should");
        }

        // compute amount with the pricing policy
        ParkingTime parkingTime = optParkingTime.get();
        BigDecimal amount = pricingPolicy.computePrice(parkingTime.getArrivalTime(), parkingTime.getDepartureTime());

        // return the bill
        Bill bill = new DefaultBill()
                .amount(amount)
                .arrivalTime(parkingTime.getArrivalTime())
                .departureTime(parkingTime.getDepartureTime());

        LOGGER.info("billing customer bill={} for parkingSlot={}", bill, parkingSlot);
        return bill;
    }

    /**
     * Allows to create a {@link TollParking}
     *
     * @return a toll parking builder
     */
    public static TollParkingBuilder create() {
        return new TollParkingBuilder();
    }

    /**
     * A builder allowing to create a {@link TollParking}.
     */
    public static class TollParkingBuilder {

        /**
         * The pricing policy
         */
        private PricingPolicy pricingPolicy;
        private List<ParkingSlotUtilization> slotUtilizationList = new ArrayList<>();

        /**
         * Sets the pricing policy
         *
         * @param pricingPolicy the pricing policy
         * @return the builder
         */
        public TollParkingBuilder pricingPolicy(PricingPolicy pricingPolicy) {
            this.pricingPolicy = pricingPolicy;
            return this;
        }

        /**
         * Sets a princing policy for each hour spent in the parking.
         *
         * @param hourPrice the hour price
         * @return the builder
         */
        public TollParkingBuilder perHourPricing(BigDecimal hourPrice) {
            this.pricingPolicy = PricingPolicy.perHour(hourPrice);
            return this;
        }

        /**
         * Sets a princing policy with a fixed amount and for each hour spent in the parking.
         *
         * @param hourPrice   the hour price
         * @param fixedAmount the fixed amount that will be added to the final price
         * @return the builder
         */
        public TollParkingBuilder perHourAndFixedAmountPricing(BigDecimal hourPrice, BigDecimal fixedAmount) {
            this.pricingPolicy = PricingPolicy.perHourAndFixedAmount(hourPrice, fixedAmount);
            return this;
        }

        /**
         * Adds a parking slot collection of a defined car type.
         *
         * @param carType       the car type
         * @param numberOfSlots the number of slots to add
         * @return the builder
         */
        public TollParkingBuilder addParkingSlot(Car.CarType carType, int numberOfSlots) {
            List<ParkingSlotUtilization> newSlots = IntStream
                    .range(0, numberOfSlots)
                    .mapToObj(i -> new ParkingSlotUtilization(ParkingSlot.of(carType, UUID.randomUUID().toString())))
                    .collect(Collectors.toList());
            slotUtilizationList.addAll(newSlots);
            return this;
        }

        /**
         * Adds a parking slot
         *
         * @param parkingSlot the parking slot to add
         * @return the builder
         */
        public TollParkingBuilder addParkingSlot(ParkingSlot parkingSlot) {
            slotUtilizationList.add(new ParkingSlotUtilization(parkingSlot));
            return this;
        }

        /**
         * Creates the toll parking
         *
         * @return a toll parking initialized
         * @throws IllegalArgumentException if the pricing policy is null
         */
        public TollParking build() {
            // check mandatory fields
            if (Objects.isNull(this.pricingPolicy)) {
                throw new IllegalArgumentException("Pricing policy must not be null");
            }
            // create parking
            TollParking parking = new TollParking();
            parking.pricingPolicy = this.pricingPolicy;
            parking.parkingSlotService = new ParkingSlotService(this.slotUtilizationList);

            return parking;
        }
    }

}
