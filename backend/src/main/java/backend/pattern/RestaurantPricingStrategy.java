package backend.pattern;

import backend.model.Ticket;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class RestaurantPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Ticket ticket) {

        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = ticket.getExitTime();

        if (exitTime == null) {
            exitTime = LocalDateTime.now();
        }

        long minutes = Duration.between(entryTime, exitTime).toMinutes();

        // First 2 hours are free for restaurant parking.
        if (minutes <= 120) {
            return BigDecimal.ZERO;
        }

        long extraMinutes = minutes - 120;
        long extraHours = (long) Math.ceil(extraMinutes / 60.0);

        BigDecimal hourlyRate;

        switch (ticket.getVehicle().getVehicleType()) {
            case BIKE -> hourlyRate = new BigDecimal("20");
            case CAR -> hourlyRate = new BigDecimal("50");
            case EV -> hourlyRate = new BigDecimal("40");
            default -> hourlyRate = new BigDecimal("50");
        }

        return hourlyRate.multiply(BigDecimal.valueOf(extraHours));
    }
}