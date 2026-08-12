package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.OrderDto;
import com.thoughtprocessing.dto.OrderHistoryDto;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.repository.OrderHistoryRepository;
import com.thoughtprocessing.repository.OrderRepository;
import com.thoughtprocessing.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("origins=http://localhost:8080")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        return orderService.getOrderWithTimeline(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/user/{uid}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(
            @PathVariable String uid,
            @RequestParam(defaultValue = "all") String range) {
        List<OrderDto> orders = orderService.getOrdersByUserAndRange(uid, range);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}/history")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(@PathVariable String orderId) {
        List<OrderHistoryDto> history = orderService.getOrderHistory(orderId);
        return ResponseEntity.ok(history);
    }
}
