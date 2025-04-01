package com.eazybooking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotels")  // Ensures the table is named correctly
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Prevents null values
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "price_per_night", nullable = false) // Ensures correct column mapping
    private Double pricePerNight;

    @Column(nullable = false)
    private Double rating; // Added rating field

    // Constructors
    public Hotel() {}

    public Hotel(String name, String location, Double pricePerNight, Double rating) {
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
