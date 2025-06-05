package com.hotel.management.pricing;

public class WeekendPricingStrategy implements PricingStrategy{
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 1.2;
    }
}
