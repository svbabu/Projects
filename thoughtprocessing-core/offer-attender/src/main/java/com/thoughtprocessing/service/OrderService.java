package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.*;
import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.repository.OrderHistoryRepository;
import com.thoughtprocessing.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.thoughtprocessing.dto.ShippingAddressDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;

    public OrderService(OrderRepository orderRepository, OrderHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    /* public Optional<Order> getOrderWithTimeline(String orderId) {
         return orderRepository.findById(orderId).map(order -> {
             List<OrderHistory> timeline = historyRepository.findByOrderOrderIdOrderByStatusTime(orderId);
             order.setTimeline(timeline);
             return order;
         });
     }*/
    @Transactional(readOnly = true)
    public Optional<OrderDto> getOrderWithTimeline(String orderId) {
        return orderRepository.findById(orderId).map(order -> {
            // Fetch timeline
            List<OrderHistory> timeline = historyRepository.findByOrderOrderIdOrderByStatusTime(orderId);

            // Map OrderHistory -> OrderHistoryDto
            List<OrderHistoryDto> timelineDtos = timeline.stream()
                    .map(h -> new OrderHistoryDto(
                            h.getHistoryId(),
                            h.getOrder().getOrderId(),
                            h.getStatus(),
                            h.getStatusTime(),
                            h.getRemarks()
                    ))
                    .toList();

            // Map Order -> OrderDto
            OrderDto dto = new OrderDto(
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getOrderStatus(),
                    order.getTotalAmount(),
                    order.getCreatedAt(),
                    order.getUpdatedAt(),
                    order.getAttempts(),
                    order.getReceipt(),
                    /*// map payments to PaymentDto
                    order.getPayments().stream()
                            .map(p -> new PaymentDto(p.getId(), p.getAmount(), p.getMethod()))
                            .toList(),*/
                    // map payments using static method
                    order.getPayments().stream()
                            .map(PaymentDto::fromEntity)   // <-- here it’s called
                            .toList(),
                    // map   to OrderItemDTO
                    order.getItems().stream()
                            .map(OrderItemDTO::fromEntity)
                            .toList(),

                    // map shipping address
                   /* new ShippingAddressDto(
                            order.getShippingAddress().getId(),
                            order.getShippingAddress().getStreetName(),
                            order.getShippingAddress().getCity(),
                            order.getShippingAddress().getState(),
                            order.getShippingAddress().getPincode()
                    )*/
                    ShippingAddressDto.fromEntity(order.getShippingAddress())
            );

            dto.setTimeline(timelineDtos);
            return dto;
        });
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByUserAndRange(String uid, String range) {
        // Step 1: Fetch orders for the user
        List<Order> orders = orderRepository.findAllByCustomerId(uid);

        // Step 2: Apply time filter
        LocalDateTime cutoff = calculateCutoff(range); // helper method
        if (cutoff != null) {
            orders = orders.stream()
                    .filter(o -> o.getCreatedAt().isAfter(cutoff))
                    .toList();
        }
// Step 3: Map entities to DTOs
        return orders.stream()
                .map(OrderDto::fromEntity)
                .toList();  // items/payments initialized here
    }

    private LocalDateTime calculateCutoff(String range) {
        LocalDateTime now = LocalDateTime.now();
        return switch (range) {
            case "1m" -> now.minusMonths(1);
            case "3m" -> now.minusMonths(3);
            case "6m" -> now.minusMonths(6);
            case "12m" -> now.minusMonths(12);
            case "all" -> null;
            default -> null;
        };
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryDto> getOrderHistory(String orderId) {
        // Step 1: Fetch timeline entries from repository
       // Order order = orderRepository.findById(orderId).orElseThrow();
        List<OrderHistory> timeline = historyRepository.findByOrderOrderIdOrderByStatusTime(orderId);

        // Step 2: Map to DTOs
        return timeline.stream()
                .map(OrderHistoryDto::fromEntity) // static mapper in OrderHistoryDto
                .toList();
    }
}



