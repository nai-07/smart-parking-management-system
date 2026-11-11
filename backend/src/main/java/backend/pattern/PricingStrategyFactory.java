package backend.pattern;

import backend.model.VenueType;

public class PricingStrategyFactory {

    private PricingStrategyFactory() {
    }

    public static PricingStrategy getStrategy(VenueType venueType) {

        return switch (venueType) {
            case MALL -> new MallPricingStrategy();
            case RESTAURANT -> new RestaurantPricingStrategy();
        };
    }
}