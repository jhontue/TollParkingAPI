package com.jhontue.parking.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Bill {

    BigDecimal getAmount();

    LocalDateTime getArrivalTime();

    LocalDateTime getDepartureTime();
}
