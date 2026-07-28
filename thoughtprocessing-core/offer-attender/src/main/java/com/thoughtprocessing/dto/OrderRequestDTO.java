package com.thoughtprocessing.dto;

import java.util.List;

public class OrderRequestDTO {
    private String orderId;
    private String customerId;
    private Long totalAmount;

    // Nested product items
    private List<OrderItemDTO> items;

    // Shipping info
    private ShippingAddressDto shippingAddress;

    // Payment info (summary only)
    private PaymentDto payment;

    public OrderRequestDTO() {}

    public OrderRequestDTO(String orderId,String customerId, Long totalAmount,
                           List<OrderItemDTO> items,
                           ShippingAddressDto shippingAddress,
                           PaymentDto payment) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.items = items;
        this.shippingAddress = shippingAddress;
        this.payment = payment;
    }

    // getters and setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public Long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Long totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }

    public ShippingAddressDto getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddressDto shippingAddress) { this.shippingAddress = shippingAddress; }

    public PaymentDto getPayment() { return payment; }
    public void setPayment(PaymentDto payment) { this.payment = payment; }

   /* public String getOrderId() {
    }*/
}
