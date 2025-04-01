package com.eazybooking.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many bookings can be associated with one hotel
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false) // Ensuring userId cannot be null
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private double totalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // Enum to manage booking statuses

    // Constructors
    public Booking() {}

    // ✅ Updated constructor to match `BookingController`
    public Booking(Hotel hotel, Long userId, String name, String email, String phone, LocalDate checkInDate, LocalDate checkOutDate, double totalCost, BookingStatus status) {
        this.hotel = hotel;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Hotel getHotel() { return hotel; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public double getTotalCost() { return totalCost; }
    public BookingStatus getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
