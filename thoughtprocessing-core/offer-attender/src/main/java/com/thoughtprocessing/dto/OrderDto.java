package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private String orderId;
    private String customerId;
    private String orderStatus;
    private Long totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer attempts;
    private String receipt;

    private List<PaymentDto> payments;
    private List<OrderItemDTO> items;
    private ShippingAddressDto shippingAddress;
    private List<OrderHistoryDto> timeline;
    public OrderDto() {}
    public OrderDto(String orderId, String customerId, String orderStatus, Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt, Integer attempts, String receipt, List<PaymentDto> payments, List<OrderItemDTO> items, ShippingAddressDto shippingAddress) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.attempts = attempts;
        this.receipt = receipt;
        this.payments = payments;
        this.items = items;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public List<PaymentDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDto> payments) {
        this.payments = payments;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public ShippingAddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

//timeline

    public List<OrderHistoryDto> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<OrderHistoryDto> timeline) {
        this.timeline = timeline;
    }
    // Static mapper timeline
    public static OrderDto fromEntity(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomerId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream()
                    .map(OrderItemDTO::fromEntity)
                    .toList());
        }
        if (order.getShippingAddress() != null) {
            dto.setShippingAddress(ShippingAddressDto.fromEntity(order.getShippingAddress()));
        }

        if (order.getPayments() != null) {
            dto.setPayments(order.getPayments().stream()
                    .map(PaymentDto::fromEntity)
                    .toList());
        }

        if (order.getTimeline() != null) {
            dto.setTimeline(order.getTimeline().stream()
                    .map(OrderHistoryDto::fromEntity)
                    .toList());
        }
        return dto;
    }
}
