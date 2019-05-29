package com.jhontue.parking.api.internal;

import com.jhontue.parking.api.Car;
import com.jhontue.parking.api.ParkingSlot;
import com.jhontue.parking.api.ParkingTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSlotServiceTest {

    @Test
    public void checkin() {

        Car gasolineCar = Car.of(Car.CarType.GASOLINE);

        ParkingSlot parkingSlot = mock(ParkingSlot.class);

        ParkingSlotUtilization slot = mock(ParkingSlotUtilization.class);
        when(slot.isFree()).thenReturn(true);
        when(slot.isCompatibleWith(gasolineCar)).thenReturn(true);
        when(slot.getParkingSlot()).thenReturn(parkingSlot);

        List<ParkingSlotUtilization> list = new ArrayList<>();
        list.add(slot);

        ParkingSlotService service = new ParkingSlotService(list);

        // call checkin
        Optional<ParkingSlot> optionalParkingSlot = service.checkin(gasolineCar);

        assertNotNull(optionalParkingSlot);
        assertTrue(optionalParkingSlot.isPresent());
        verify(slot).setCar(gasolineCar);
        verify(slot, times(1)).setArrivalTime(any());
        verify(slot).setDepartureTime(null);
    }

    @Test
    public void checkout() {
        ParkingSlot parkingSlot = mock(ParkingSlot.class);
        when(parkingSlot.getId()).thenReturn("ID");

        ParkingSlotUtilization slot = mock(ParkingSlotUtilization.class);
        when(slot.isFree()).thenReturn(false);
        when(slot.getParkingSlot()).thenReturn(parkingSlot);

        List<ParkingSlotUtilization> list = new ArrayList<>();
        list.add(slot);

        ParkingSlotService service = new ParkingSlotService(list);

        // call checkin
        Optional<ParkingTime> parkingTime = service.checkout(parkingSlot);

        assertNotNull(parkingTime);
        verify(slot).setCar(null);
        verify(slot, times(1)).setDepartureTime(any());
    }
}