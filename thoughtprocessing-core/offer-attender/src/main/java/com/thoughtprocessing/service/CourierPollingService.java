package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.CourierEventPayloadDto;
import com.thoughtprocessing.dto.OrderHistoryDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourierPollingService {

    private final OrderService orderService;
    private final CourierApiClient courierApiClient;

    public CourierPollingService(OrderService orderService, CourierApiClient courierApiClient) {
        this.orderService = orderService;
        this.courierApiClient = courierApiClient;
    }

    // Run every 15 minutes
    @Scheduled(fixedRate = 15 * 60 * 1000)
    @Transactional
    public void pollCourierUpdates() {
        // Fetch active orders from your DB/service
        List<String> activeOrderIds = orderService.getActiveOrderIds();

        for (String orderId : activeOrderIds) {
            syncCourierTimeline(orderId);
        }
    }

    public void syncCourierTimeline(String orderId) {
        // Step 2a: Fetch tracking history from courier API
        List<CourierEventPayloadDto> events = courierApiClient.getTrackingHistory(orderId);

        // Step 2b: Get existing history from DB
        List<OrderHistoryDto> existing = orderService.getOrderHistory(orderId);

        // Step 2c: Insert only new events
        for (CourierEventPayloadDto payload : events) {
            boolean alreadyExists = existing.stream()
                    .anyMatch(h -> h.getStatus().equals(payload.getStatus())
                            && h.getStatusTime().equals(payload.getTime()));

            if (!alreadyExists) {
                OrderHistoryDto dto = new OrderHistoryDto();
                dto.setOrderId(payload.getOrderId());
                dto.setStatus(payload.getStatus());
                dto.setStatusTime(payload.getTime());
                dto.setRemarks(payload.getRemarks());

                orderService.addHistoryEntry(orderId, dto);
            }
        }
    }
}
