package com.eazybooking.controller;

import com.eazybooking.service.CarRentalService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/car-rentals")
public class CarRentalController {

    private final CarRentalService carRentalService;

    @Autowired
    public CarRentalController(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }

    @GetMapping
    public ResponseEntity<?> getCarRentals(
            @RequestParam String pickUpDate,
            @RequestParam String dropOffDate,
            @RequestParam String pickUpTime,
            @RequestParam String dropOffTime) {

        // Call the service and return response
        return ResponseEntity.ok(carRentalService.searchCarRentals(pickUpDate, dropOffDate, pickUpTime, dropOffTime));
    }

    @GetMapping("/auto-complete")
    public ResponseEntity<?> autoComplete(@RequestParam String query) {
        return ResponseEntity.ok(carRentalService.autoComplete(query));
    }
}
