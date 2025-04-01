package com.eazybooking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;

@Service
public class PaystackService {

    @Value("${paystack.secret-key}")
    private String secretKey;

    @Value("${paystack.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> initializePayment(String email, double amount) {
        String url = baseUrl + "transaction/initialize";
        int amountInCents = (int) (amount * 100);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("amount", amountInCents);
        requestBody.put("currency", "KES");
        requestBody.put("callback_url", "https://yourapp.com/payment/callback");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public ResponseEntity<?> verifyPayment(String reference) {
        String url = baseUrl + "transaction/verify/" + reference;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Payment verification failed");
        }
    }

    public ResponseEntity<?> refundPayment(String transactionId, double amount) {
        String url = baseUrl + "refund";
        int amountInCents = (int) (amount * 100);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("transaction", transactionId);
        requestBody.put("amount", amountInCents);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
