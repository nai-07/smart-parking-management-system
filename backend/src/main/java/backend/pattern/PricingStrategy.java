package backend.pattern;

import backend.model.Ticket;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Ticket ticket);
}