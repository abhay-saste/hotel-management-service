package com.hotel.management.pricing;

public class PricingStrategyFactory {
    public static PricingStrategy getStrategy(String context) {
        return switch (context.toLowerCase()) {
            case "weekend" -> new WeekendPricingStrategy();
            case "event" -> new EventSeasonPricingStrategy();
            default -> basePrice -> basePrice;
        };
    }
}
