package com.eazybooking.controller;

import com.eazybooking.model.Booking;
import com.eazybooking.model.Hotel;
import com.eazybooking.model.BookingStatus;
import com.eazybooking.service.BookingService;
import com.eazybooking.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final HotelService hotelService; // Inject HotelService to retrieve hotel details

    public BookingController(BookingService bookingService, HotelService hotelService) {
        this.bookingService = bookingService;
        this.hotelService = hotelService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        Optional<Hotel> hotelOptional = hotelService.getHotelById(request.getHotelId());
        if (hotelOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid hotel ID");
        }

        try {
            LocalDate checkInDate = LocalDate.parse(request.getCheckInDate());
            LocalDate checkOutDate = LocalDate.parse(request.getCheckOutDate());

            // ✅ Booking model now supports all fields, so we keep all parameters
            Booking booking = new Booking(
                    hotelOptional.get(),
                    request.getUserId(),
                    request.getName(),
                    request.getEmail(),
                    request.getPhone(),
                    checkInDate,
                    checkOutDate,
                    request.getTotalCost(),
                    BookingStatus.PENDING // Default status
            );

            Booking savedBooking = bookingService.saveBooking(booking);
            return ResponseEntity.ok(savedBooking);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use 'YYYY-MM-DD'.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingRequest request) {
        Optional<Booking> existingBooking = bookingService.getBookingById(id);
        if (existingBooking.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Booking booking = existingBooking.get();

            if (request.getCheckInDate() != null) {
                booking.setCheckInDate(LocalDate.parse(request.getCheckInDate()));
            }
            if (request.getCheckOutDate() != null) {
                booking.setCheckOutDate(LocalDate.parse(request.getCheckOutDate()));
            }
            if (request.getTotalCost() > 0) {
                booking.setTotalCost(request.getTotalCost());
            }
            if (request.getStatus() != null) {
                booking.setStatus(BookingStatus.valueOf(request.getStatus()));
            }

            Booking updatedBooking = bookingService.saveBooking(booking);
            return ResponseEntity.ok(updatedBooking);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use 'YYYY-MM-DD'.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // DTO class to handle the request body
    public static class BookingRequest {
        private Long userId;
        private Long hotelId;
        private String name;
        private String email;
        private String phone;
        private String checkInDate;
        private String checkOutDate;
        private double totalCost;
        private String status;

        public Long getUserId() { return userId; }
        public Long getHotelId() { return hotelId; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getCheckInDate() { return checkInDate; }
        public String getCheckOutDate() { return checkOutDate; }
        public double getTotalCost() { return totalCost; }
        public String getStatus() { return status; }
    }
}
