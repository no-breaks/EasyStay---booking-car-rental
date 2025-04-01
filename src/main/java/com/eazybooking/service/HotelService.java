package com.eazybooking.service;

import com.eazybooking.model.Hotel;
import com.eazybooking.repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public Hotel updateHotel(Long id, Hotel updatedHotel) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setName(updatedHotel.getName());
        hotel.setLocation(updatedHotel.getLocation());
        hotel.setPricePerNight(updatedHotel.getPricePerNight());
        hotel.setRating(updatedHotel.getRating());
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotelRating(Long id, double rating) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setRating(rating);
        return hotelRepository.save(hotel);
    }
}
