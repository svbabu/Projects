package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.*;
import com.thoughtprocessing.exception.OrderNotFoundException;
import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.repository.OrderHistoryRepository;
import com.thoughtprocessing.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.thoughtprocessing.dto.ShippingAddressDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;

    public OrderService(OrderRepository orderRepository, OrderHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

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

                    // map payments using static method
                    //map payments to PaymentDto
                    order.getPayments().stream()
                            .map(PaymentDto::fromEntity)   // <-- here it’s called
                            .toList(),
                    // map   to OrderItemDTO
                    order.getItems().stream()
                            .map(OrderItemDTO::fromEntity)
                            .toList(),

                    // map shipping address

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
    @Transactional(readOnly = true)
    public Map<String, List<OrderHistoryDto>> getAllTimelines() {
        // Step 1: Fetch all history entries
        List<OrderHistory> allHistories = historyRepository.findAll();

        // Step 2: Map entities to DTOs
        List<OrderHistoryDto> dtos = allHistories.stream()
                .map(OrderHistoryDto::fromEntity)
                .toList();

        // Step 3: Group by orderId
        return dtos.stream()
                .collect(Collectors.groupingBy(OrderHistoryDto::getOrderId));
    }

    @Transactional
    public OrderHistoryDto addHistoryEntry(String orderId, OrderHistoryDto dto) {
        // Step 1: Fetch the order entity and updated customexception
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException(orderId));
               // .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        // Step 2: Create a new OrderHistory entity
        OrderHistory history = new OrderHistory(
                order,
                dto.getStatus(),
                dto.getStatusTime() != null ? dto.getStatusTime() : LocalDateTime.now(),
                dto.getRemarks()
        );

        // Step 3: Save to repository
        OrderHistory saved = historyRepository.save(history);

        // Step 4: Map back to DTO
        return OrderHistoryDto.fromEntity(saved);
    }
    @Transactional(readOnly = true)
    public List<String> getActiveOrderIds() {
        // Define active statuses
        List<String> activeStatuses = List.of("PLACED", "PACKED", "SHIPPED", "OUT_FOR_DELIVERY");

        // Query DB
        List<Order> activeOrders = orderRepository.findByOrderStatusIn(activeStatuses);

        // Map to orderId list
        return activeOrders.stream()
                .map(Order::getOrderId)
                .toList();
    }

}



