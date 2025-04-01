package com.eazybooking.controller;

import com.eazybooking.service.PaystackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaystackService paystackService;

    public PaymentController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    /**
     * Endpoint to initialize a payment
     * Supports both query parameters and JSON body input.
     * @param email (Optional) Customer email (from query parameter or request body)
     * @param amount (Optional) Amount to pay (from query parameter or request body)
     * @param requestBody (Optional) JSON body containing "email" and "amount"
     * @return ResponseEntity with payment details
     */
    @PostMapping("/initialize")
    public ResponseEntity<?> initializePayment(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Double amount,
            @RequestBody(required = false) Map<String, Object> requestBody) {

        // Extract values from requestBody if query parameters are missing
        if (email == null && requestBody != null) {
            email = (String) requestBody.get("email");
            amount = ((Number) requestBody.get("amount")).doubleValue();
        }

        // Validate required fields
        if (email == null || amount == null) {
            return ResponseEntity.badRequest().body("Missing email or amount");
        }

        return paystackService.initializePayment(email, amount);
    }

    /**
     * Endpoint to verify a payment
     * @param reference Paystack transaction reference
     * @return ResponseEntity with verification details
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestParam String reference) {
        return paystackService.verifyPayment(reference);
    }

    /**
     * Endpoint to process a refund
     * @param transactionId The transaction ID from Paystack
     * @param amount The amount to be refunded
     * @return ResponseEntity with refund details
     */
    @PostMapping("/refund")
    public ResponseEntity<?> initiateRefund(@RequestParam String transactionId, @RequestParam double amount) {
        return paystackService.refundPayment(transactionId, amount);
    }
}
