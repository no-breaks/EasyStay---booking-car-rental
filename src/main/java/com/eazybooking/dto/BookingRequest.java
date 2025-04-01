package com.eazybooking.dto;

public class BookingRequest { // Removed static ✅
    private Long userId;
    private Long hotelId;
    private String name;
    private String email;
    private String phone;
    private String checkInDate;
    private String checkOutDate;
    private double totalCost;

    // Getters
    public Long getUserId() { return userId; }
    public Long getHotelId() { return hotelId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public double getTotalCost() { return totalCost; }
}
