package backend.service;

import backend.model.Ticket;
import backend.model.VenueType;
import backend.pattern.PricingStrategy;
import backend.pattern.PricingStrategyFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillingService {

    private final ValidationService validationService;

    public BillingService(ValidationService validationService) {
        this.validationService = validationService;
    }

    public BigDecimal calculateFee(Ticket ticket) {

        PricingStrategy strategy =
                PricingStrategyFactory.getStrategy(
                        ticket.getVenue().getVenueType()
                );

        return strategy.calculatePrice(ticket);
    }

    public BigDecimal calculateFee(
            Ticket ticket,
            String validationCode) {

        BigDecimal amount = calculateFee(ticket);

        if (validationCode == null ||
                validationCode.isBlank() ||
                ticket.getVenue().getVenueType()
                        != VenueType.RESTAURANT) {

            return amount;
        }

        try {
            validationService.findByCode(validationCode);

            BigDecimal discount =
                    amount.multiply(
                            new BigDecimal("0.20")
                    );

            return amount.subtract(discount)
                    .max(BigDecimal.ZERO);

        } catch (Exception e) {
            return amount;
        }
    }
}