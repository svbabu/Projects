package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.OrderItemDTO;
import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderEntity;
import com.thoughtprocessing.model.OrderItemEntity;
import com.thoughtprocessing.repository.OrderEntityRepository;
import com.thoughtprocessing.repository.OrderItemRepository;
import com.thoughtprocessing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemsService {
    private static final Logger logger = LoggerFactory.getLogger(ItemsService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Save items for an order
    public OrderItemEntity addItemToOrder(String orderId, OrderItemEntity item) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        item.setOrder(order); // attach relationship
        logger.info("Adding item {} to order {}", item.getProductName(), orderId);
        return orderItemRepository.save(item);
    }

    // Fetch all items for an order
    public List<OrderItemEntity> getItemsByOrderId(String orderId) {
        logger.info("Fetching items for order {}", orderId);
        return orderItemRepository.findByOrderOrderId(orderId);
    }

    // Fetch order with items (history/details)
    /*public Optional<OrderEntity> getOrderWithItems(String orderId) {
        logger.info("Fetching order with items: {}", orderId);
        return orderEntityRepository.findOrderWithItems(orderId);
    }*/
    public Optional<Order> getOrderWithItems(String orderId) {
        List<Order> results = orderRepository.findOrderWithItems(orderId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    // Update item details (e.g., quantity, price)
    public OrderItemEntity updateItem(OrderItemEntity item) {
        logger.info("Updating item {}", item.getId());
        return orderItemRepository.save(item);
    }

    // Remove item from order
    public void removeItem(Long itemId) {
        logger.info("Removing item {}", itemId);
        orderItemRepository.deleteById(itemId);
    }
    public OrderItemDTO toDTO(OrderItemEntity entity) {
        return new OrderItemDTO(
                entity.getProductId(),
                entity.getProductName(),
                entity.getModelName(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getQuantity(),
                entity.getBasePrice(),
                entity.getAppliedPrice(),
                entity.getDiscountPercentage(),
                entity.getOfferId()
        );
    }
    public List<OrderItemDTO> getItemDTOsByOrderId(String orderId) {
        return orderItemRepository.findByOrderOrderId(orderId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


}

