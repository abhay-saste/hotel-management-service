package com.hotel.management.pricing;

public class EventSeasonPricingStrategy implements PricingStrategy{
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 1.5;
    }
}
