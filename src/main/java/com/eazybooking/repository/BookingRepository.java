package com.eazybooking.repository;

import com.eazybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b")  // ✅ Ensures data retrieval
    List<Booking> findAllBookings();

    List<Booking> findByUserId(Long userId);
}
