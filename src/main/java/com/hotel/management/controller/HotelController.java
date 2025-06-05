package com.hotel.management.controller;

import com.hotel.management.dto.Hotel;
import com.hotel.management.dto.Room;
import com.hotel.management.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.ok(hotelService.addHotel(hotel));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String location, @RequestParam int guests) {
        return ResponseEntity.ok(hotelService.searchHotels(location, guests));
    }

    @GetMapping("/price")
    public ResponseEntity<Double> getRoomPrice(@RequestBody Room room,
                                               @RequestParam(defaultValue = "normal") String context) {
        return ResponseEntity.ok(hotelService.getDynamicRoomPrice(room, context));
    }
}