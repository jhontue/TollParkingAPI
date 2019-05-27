package com.jhontue.parking.api.internal;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;
import static org.junit.Assert.assertEquals;

public class PerHourPricingPolicyTest {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void computePrice() {
        PerHourPricingPolicy pricingPolicy = new PerHourPricingPolicy(new BigDecimal("2.00"));

        BigDecimal twoHoursPrice = pricingPolicy.computePrice(parse("2016-11-09 10:00", FORMATTER), parse("2016-11-09 12:00", FORMATTER));
        BigDecimal halfHourPrice = pricingPolicy.computePrice(parse("2016-11-09 10:30", FORMATTER), parse("2016-11-09 11:00", FORMATTER));
        BigDecimal tenMinPrice = pricingPolicy.computePrice(parse("2016-11-09 10:50", FORMATTER), parse("2016-11-09 11:00", FORMATTER));

        assertEquals(new BigDecimal("4.00"), twoHoursPrice);
        assertEquals(new BigDecimal("1.00"), halfHourPrice);
        assertEquals(new BigDecimal("0.34"), tenMinPrice);
    }
}