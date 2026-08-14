package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.CourierEventPayloadDto;
import com.thoughtprocessing.dto.OrderHistoryDto;
import com.thoughtprocessing.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier")
@CrossOrigin("origins=http://localhost:8080")
public class CourierWebhookController {
    private final OrderService orderService;

    public CourierWebhookController(OrderService orderService) {
        this.orderService = orderService;
    }
    // Example: courier sends tracking updates
    @PostMapping("/webhook")
    public ResponseEntity<?> receiveCourierEvent(@Valid @RequestBody CourierEventPayloadDto payload) {
        try {
            OrderHistoryDto dto = new OrderHistoryDto();
            dto.setOrderId(payload.getOrderId());
            dto.setStatus(payload.getStatus());
            dto.setStatusTime(payload.getTime());
            dto.setRemarks(payload.getRemarks());

            OrderHistoryDto saved = orderService.addHistoryEntry(payload.getOrderId(), dto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process courier event: " + e.getMessage());
        }
    }
}
