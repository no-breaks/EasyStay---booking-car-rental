package com.eazybooking.service; // ✅ Correct package declaration

import com.eazybooking.model.Booking;
import com.eazybooking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional  // ✅ Ensures transactions are managed properly
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAllBookings(); // ✅ Use custom query
        System.out.println("📌 Retrieved bookings: " + bookings);  // ✅ Debugging log
        return bookings;
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking saveBooking(Booking booking) {
        Booking saved = bookingRepository.save(booking);
        System.out.println("✅ Booking saved: " + saved);  // ✅ Debugging log
        return saved;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}

