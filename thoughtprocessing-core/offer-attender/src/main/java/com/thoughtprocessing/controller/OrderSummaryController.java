package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.OrderItemDTO;
import com.thoughtprocessing.dto.OrderRequestDTO;
import com.thoughtprocessing.dto.OrderResponseDTO;
import com.thoughtprocessing.dto.OrderSuccessDTO;
import com.thoughtprocessing.model.OrderEntity;
import com.thoughtprocessing.model.ShippingAddressEntity;
import com.thoughtprocessing.service.CheckoutService;
import com.thoughtprocessing.service.ItemsService;
import com.thoughtprocessing.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("origins=http://localhost:8080")
public class OrderSummaryController {
    private final CheckoutService checkoutService;
    public OrderSummaryController(CheckoutService checkoutService)
    {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/{orderId}/full-details")
    public ResponseEntity<OrderResponseDTO> getFullOrderDetails(@PathVariable String orderId) {
        // Delegate to CheckoutService
        OrderResponseDTO response = checkoutService.getFullOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO request) throws Exception {
        try {
            OrderResponseDTO response = checkoutService.createOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create order: " + e.getMessage()));
        }
    }
    @GetMapping("/shipping/default/{userId}")
    public ResponseEntity<ShippingAddressEntity> getDefaultShippingAddress(@PathVariable String userId) {
        return ResponseEntity.ok(checkoutService.getDefaultShippingAddress(userId));
    }

}
    /*private final PaymentService paymentService;
    private final ItemsService itemsService;

    public OrderSummaryController(PaymentService paymentService, ItemsService itemsService) {
        this.paymentService = paymentService;
        this.itemsService = itemsService;
    }

    @GetMapping("/{orderId}/full-details")
    public ResponseEntity<OrderResponseDTO> getFullOrderDetails(@PathVariable String orderId) {
        OrderEntity orderEntity = itemsService.getOrderWithItems(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        Optional<OrderSuccessDTO> paymentDetails = paymentService.getOrderSuccDetls(orderId);

        List<OrderItemDTO> itemDTOs = orderEntity.getItems()
                .stream()
                .map(itemsService::toDTO)   // or inline mapping
                .collect(Collectors.toList());

        OrderResponseDTO response = new OrderResponseDTO(
                orderEntity.getOrderId(),
                orderEntity.getCustomerId(),
                orderEntity.getOrderStatus(),
                orderEntity.getTotalAmount(),
                orderEntity.getCreatedAt(),
                orderEntity.getUpdatedAt(),
                orderEntity.getReceipt(),
                itemDTOs,
                paymentDetails.orElse(null)
        );

        return ResponseEntity.ok(response);
    }
*/


