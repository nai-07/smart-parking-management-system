package backend;

import backend.model.*;
import backend.pattern.MallPricingStrategy;
import backend.pattern.RestaurantPricingStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSystemTest {

    @Test
    void mallPricingShouldCalculateFee() {

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(VehicleType.CAR);

        Venue venue = new Venue();
        venue.setVenueType(VenueType.MALL);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setVenue(venue);

        ticket.setEntryTime(
                LocalDateTime.now().minusHours(2)
        );

        ticket.setExitTime(
                LocalDateTime.now()
        );

        BigDecimal fee =
                new MallPricingStrategy()
                        .calculatePrice(ticket);

        assertTrue(
                fee.compareTo(BigDecimal.ZERO) > 0
        );
    }

    @Test
    void restaurantFirstTwoHoursShouldBeFree() {

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(VehicleType.CAR);

        Venue venue = new Venue();
        venue.setVenueType(VenueType.RESTAURANT);

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setVenue(venue);

        ticket.setEntryTime(
                LocalDateTime.now().minusHours(1)
        );

        ticket.setExitTime(
                LocalDateTime.now()
        );

        BigDecimal fee =
                new RestaurantPricingStrategy()
                        .calculatePrice(ticket);

        assertEquals(
                BigDecimal.ZERO,
                fee
        );
    }

    @Test
    void slotShouldChangeToOccupied() {

        Slot slot = new Slot();

        slot.setStatus(SlotStatus.FREE);

        slot.setStatus(SlotStatus.OCCUPIED);

        assertEquals(
                SlotStatus.OCCUPIED,
                slot.getStatus()
        );
    }
}