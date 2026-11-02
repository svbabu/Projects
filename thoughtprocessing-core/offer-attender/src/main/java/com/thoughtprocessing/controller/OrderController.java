package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.OrderDto;
import com.thoughtprocessing.dto.OrderHistoryDto;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.repository.OrderHistoryRepository;
import com.thoughtprocessing.repository.OrderRepository;
import com.thoughtprocessing.service.OrderPaymentCoordinatorService;
import com.thoughtprocessing.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("origins=http://localhost:8080")
public class OrderController {
    private final OrderService orderService;
    private final OrderPaymentCoordinatorService coordinator;

    public OrderController(OrderService orderService,OrderPaymentCoordinatorService coordinator) {

        this.orderService = orderService;
        this.coordinator =coordinator;
    }
    @PostMapping("/order-with-payment")
    public OrderDto createOrderWithPayment(@RequestParam String customerId,
                                           @RequestParam Long amount,
                                           @RequestParam String receipt,
                                           @RequestParam(required=false) Long shippingAddressId,
                                           @RequestParam String method,
                                           @RequestParam(required=false) String bankOrUpiId) throws Exception {
        return coordinator.createOrderWithPayment(customerId, amount, receipt, shippingAddressId, method, bankOrUpiId);
    }
     //fetch order with timeline
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
    //fetch order history
    @GetMapping("/{orderId}/history")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(@PathVariable String orderId) {
        List<OrderHistoryDto> history = orderService.getOrderHistory(orderId);
        return ResponseEntity.ok(history);
    }
    // all orders with timeline
    @GetMapping("/timeline")
    public ResponseEntity<Map<String, List<OrderHistoryDto>>> getAllOrderTimelines() {
        Map<String, List<OrderHistoryDto>> timelines = orderService.getAllTimelines();
        return ResponseEntity.ok(timelines);
    }
    //Latest status for an order (quick lookup)
    @GetMapping("/{orderId}/status")
    public ResponseEntity<String> getLatestStatus(@PathVariable String orderId) {
        List<OrderHistoryDto> history = orderService.getOrderHistory(orderId);
        if (history.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String latestStatus = history.get(history.size() - 1).getStatus();
        return ResponseEntity.ok(latestStatus);
    }
    //Add a new history entry (manual update or courier webhook)
    //This allows backend or courier integration to append new timeline events.
    //POST /api/orders/{orderId}/history → manual update (admin/warehouse staff can append statuses like PACKED, CANCELLED, etc.)
    @PostMapping("/{orderId}/history")
    public ResponseEntity<OrderHistoryDto> addHistoryEntry(
            @PathVariable String orderId,
           @Valid @RequestBody OrderHistoryDto dto) {
        OrderHistoryDto saved = orderService.addHistoryEntry(orderId, dto);
        return ResponseEntity.ok(saved);
    }
    //manual update by admin staff
    @PostMapping("/{orderId}/ship")
    public ResponseEntity<Void> markAsShipped(@PathVariable String orderId) {
        orderService.markAsShipped(orderId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<Void> markAsDelivered(@PathVariable String orderId) {
        orderService.markAsDelivered(orderId);
        return ResponseEntity.ok().build();
    }
}
