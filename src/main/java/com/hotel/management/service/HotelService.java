package com.hotel.management.service;

import com.hotel.management.dto.Amenity;
import com.hotel.management.dto.Hotel;
import com.hotel.management.dto.Room;
import com.hotel.management.pricing.PricingStrategy;
import com.hotel.management.pricing.PricingStrategyFactory;
import com.hotel.management.pricing.WeekendPricingStrategy;
import com.hotel.management.repository.AmenityRepository;
import com.hotel.management.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;

    private final AmenityRepository amenityRepository;

    public Hotel addHotel(Hotel hotel) {
        hotel.getRooms().forEach(r -> r.setHotel(hotel));

        List<Amenity> resolvedAmenities = new ArrayList<>();
        for (Amenity amenity : hotel.getAmenities()) {
            String normalized = amenity.getName().trim().toLowerCase();
            amenityRepository.findByNameIgnoreCase(normalized)
                    .ifPresentOrElse(resolvedAmenities::add, () -> {
                        amenity.setName(normalized);
                        resolvedAmenities.add(amenity);
                    });
        }
        hotel.setAmenities(resolvedAmenities);

        return hotelRepository.save(hotel);
    }

    @Cacheable(value = "hotels", key = "#location.concat('-').concat(#guests.toString())")
    public List<Hotel> searchHotels(String location, int guests) {
        return hotelRepository.findByLocationIgnoreCase(location).stream()
                .filter(h -> h.getRooms().stream().anyMatch(r -> r.getCapacity() >= guests && r.isAvailable()))
                .collect(Collectors.toList());
    }

    public double getDynamicRoomPrice(Room room, String context) {
        PricingStrategy strategy = PricingStrategyFactory.getStrategy(context);
        return strategy.calculatePrice(room.getPrice());
    }
}
