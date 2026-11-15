package com.thoughtprocessing.helper;
import com.thoughtprocessing.exception.OrderNotFoundException;
import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.repository.OrderRepository;
import com.thoughtprocessing.repository.OrderHistoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class OrderStatusUpdater {
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository historyRepository;

    public OrderStatusUpdater(OrderRepository orderRepository,
                              OrderHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }
    /*@Transactional
    public void update(Order order, String status, String remarks) {
        // Update order
        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Save history
        OrderHistory history = new OrderHistory(order, status, LocalDateTime.now(), remarks);
        historyRepository.save(history);
    }*/
    @Transactional
    public void updateOrderStatus(Order order, String status, String remarks) {
        // Update the order itself
        order.setOrderStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Add a history entry
        OrderHistory history = new OrderHistory(order, status, LocalDateTime.now(), remarks);
        historyRepository.save(history);
    }
    @Transactional
    public void markAsShipped(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        updateOrderStatus(order, "SHIPPED", "Shipped via courier");
    }

    @Transactional
    public void markAsDelivered(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        updateOrderStatus(order, "DELIVERED", "Delivered to customer");
    }
    @Transactional
    public void markOrder(String orderId, String status, String remarks) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        updateOrderStatus(order, status, remarks);
    }

}
