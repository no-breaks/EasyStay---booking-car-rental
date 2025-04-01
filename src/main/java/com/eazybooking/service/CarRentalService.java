package com.eazybooking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

@Service
public class CarRentalService {

    private static final Logger logger = LoggerFactory.getLogger(CarRentalService.class);

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    @Value("${rapidapi.cars.search-url}")
    private String baseUrl;

    @Value("${rapidapi.cars.auto-complete-url}")
    private String autoCompleteUrl;

    public String searchCarRentals(String pickUpDate, String dropOffDate, String pickUpTime, String dropOffTime) {
        try {
            String queryParams = String.format(
                    "?pickUpEntityId=95565058&pickUpDate=%s&dropOffDate=%s&pickUpTime=%s&dropOffTime=%s",
                    encode(pickUpDate), encode(dropOffDate), encode(pickUpTime), encode(dropOffTime));

            HttpRequest request = buildRequest(baseUrl + queryParams);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return handleResponse(response);
        } catch (Exception e) {
            logger.error("Error fetching car rentals: {}", e.getMessage(), e);
            return "Error fetching car rentals.";
        }
    }

    public String autoComplete(String query) {
        try {
            String queryParams = String.format("?query=%s", encode(query));

            HttpRequest request = buildRequest(autoCompleteUrl + queryParams);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return handleResponse(response);
        } catch (Exception e) {
            logger.error("Error fetching auto-complete results: {}", e.getMessage(), e);
            return "Error fetching auto-complete results.";
        }
    }

    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-rapidapi-key", rapidApiKey)
                .header("x-rapidapi-host", rapidApiHost)
                .GET()
                .build();
    }

    private String handleResponse(HttpResponse<String> response) throws IOException {
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            logger.warn("API request failed with status: {}", response.statusCode());
            throw new IOException("Failed request with status: " + response.statusCode());
        }
    }

    private String encode(String value) {
        return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
