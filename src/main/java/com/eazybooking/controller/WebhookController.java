package com.eazybooking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @PostMapping("/refund")
    public ResponseEntity<String> handleRefundWebhook(@RequestBody Map<String, Object> payload) {
        logger.info("Received refund webhook: {}", payload);

        // Extract refund status
        String status = (String) payload.get("status");
        String refundId = (String) payload.get("refund_id");

        if ("completed".equalsIgnoreCase(status)) {
            // Update database or notify user
            logger.info("Refund {} is completed.", refundId);
            // TODO: Update refund status in database
        }

        return ResponseEntity.status(HttpStatus.OK).body("Webhook received");
    }
}
