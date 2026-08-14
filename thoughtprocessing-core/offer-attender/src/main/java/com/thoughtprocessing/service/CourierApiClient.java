package com.thoughtprocessing.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtprocessing.dto.CourierEventPayloadDto;
import com.thoughtprocessing.dto.OrderHistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CourierApiClient {
    private static final Logger logger = LoggerFactory.getLogger(CourierApiClient.class);

    private final RestTemplate restTemplate = new RestTemplate();
    //private final String token = "YOUR_DELHIVERY_API_TOKEN";

    @Value("${delhivery.api.base-url}")
    private String baseUrl;
    @Value("${delhivery.api.token}")
    private String token;



    public List<CourierEventPayloadDto> getTrackingHistory(String waybill) {
        logger.info("Fetching tracking history for waybill {}", waybill);
        //String url = "https://track.delhivery.com/api/v1/packages/json/?waybill="
                //+ waybill + "&token=" + token;
        String url = baseUrl + "?waybill=" + waybill + "&token=" + token;
        //logger.info("Fetching tracking history for waybill {}", waybill);
        try {
            // Fetch raw JSON as String
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode root = mapper.readTree(response.getBody());

            // Validate response
            if (!root.has("ShipmentData")) {
                throw new RuntimeException("Invalid response from courier API for " + waybill);
            }

            JsonNode shipmentData = root.get("ShipmentData").get(0).get("Shipment");
            JsonNode scans = shipmentData.get("Scans");

            List<CourierEventPayloadDto> events = new ArrayList<>();
            for (JsonNode scan : scans) {
                events.add(new CourierEventPayloadDto(
                        waybill,
                        mapStatus(scan.get("Scan").asText()),
                        LocalDateTime.parse(scan.get("ScanDateTime").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        scan.get("Scan").asText()
                ));
            }
            return events;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch tracking history for " + waybill, e);
        }
    }
    private String mapStatus(String courierStatus) {
        return switch (courierStatus) {
            case "Shipment Created" -> "PLACED";
            case "Picked Up" -> "PACKED";
            case "In Transit" -> "SHIPPED";
            case "Out for Delivery" -> "OUT_FOR_DELIVERY";
            case "Delivered" -> "DELIVERED";
            default -> "UNKNOWN";
        };
    }

}

