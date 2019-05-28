package com.jhontue.parking.api.internal;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;
import static org.junit.Assert.*;

/**
 * Test of {@link PerHourAndFixedAmountPricingPolicy}
 */
public class PerHourAndFixedAmountPricingPolicyTest {

    /**
     * Date formatter
     */
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void computePrice() {
        PerHourAndFixedAmountPricingPolicy pricingPolicy = new PerHourAndFixedAmountPricingPolicy(new BigDecimal(5), new BigDecimal(10));

        BigDecimal twoHoursPrice = pricingPolicy.computePrice(parse("2016-11-09 10:00", FORMATTER), parse("2016-11-09 12:00", FORMATTER));
        BigDecimal halfHourPrice = pricingPolicy.computePrice(parse("2016-11-09 10:30", FORMATTER), parse("2016-11-09 11:00", FORMATTER));
        BigDecimal tenMinPrice = pricingPolicy.computePrice(parse("2016-11-09 10:50", FORMATTER), parse("2016-11-09 11:00", FORMATTER));

        assertEquals(new BigDecimal("20.00"), twoHoursPrice);
        assertEquals(new BigDecimal("12.50"), halfHourPrice);
        assertEquals(new BigDecimal("10.85"), tenMinPrice);
    }
}