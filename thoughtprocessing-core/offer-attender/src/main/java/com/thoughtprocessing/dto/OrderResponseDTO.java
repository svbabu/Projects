package com.thoughtprocessing.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private String orderId;
    private String customerId;
    private String orderStatus; // CREATED, PACKED, SHIPPED, DELIVERED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long totalAmount;

    // Nested product items
    private List<OrderItemDTO> items;

    // Shipping info
    private String recipientName;
    private String address;
    private String mobile;

    // Payment info (summary only)
    private String paymentMethod;
    private String receipt;
    // Razorpay order ID (new field)
    private String razorpayOrderId;
    // Optional: embed payment success details if needed
    private OrderSuccessDTO paymentDetails;
    public OrderResponseDTO(){}
    public OrderResponseDTO(String orderId,String razorpayOrderId, String customerId, String orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt, Long totalAmount, List<OrderItemDTO> items, String recipientName, String address, String mobile, String paymentMethod, String receipt, OrderSuccessDTO paymentDetails) {
        this.orderId = orderId;
        this.razorpayOrderId = razorpayOrderId;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalAmount = totalAmount;
        this.items = items;
        this.recipientName = recipientName;
        this.address = address;
        this.mobile = mobile;
        this.paymentMethod = paymentMethod;
        this.receipt = receipt;
        this.paymentDetails = paymentDetails;
    }

    public OrderResponseDTO(String orderId,String razorpayOrderId, String customerId, String orderStatus, Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt, String receipt, List<OrderItemDTO> items, OrderSuccessDTO orderSuccessDTO) {
    this.orderId = orderId;
    this.razorpayOrderId = razorpayOrderId;
    this.customerId = customerId;
    this.orderStatus = orderStatus;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.totalAmount = totalAmount;
    this.createdAt=createdAt;
    this.updatedAt=updatedAt;
    this.receipt = receipt;
    this.items=items;
    this.paymentDetails = orderSuccessDTO;
    }


    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
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

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public OrderSuccessDTO getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(OrderSuccessDTO paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}

