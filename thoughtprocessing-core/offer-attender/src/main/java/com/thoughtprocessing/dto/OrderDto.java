package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.model.ShippingAddressEntity;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private String orderId;
    private String razorpayOrderId; // external Razorpay orderId

    private String customerId;
    private String orderStatus;
    private Long totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer attempts;
    private String receipt;
    private Long shippingAddressId;
    private List<PaymentDto> payments;
    private List<OrderItemDTO> items;
    private ShippingAddressDto shippingAddress;
    private List<OrderHistoryDto> timeline;
    public OrderDto() {}
   /* public OrderDto(String orderId,String razorpayOrderId, String customerId, String orderStatus, Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt, Integer attempts, String receipt, List<PaymentDto> payments, List<OrderItemDTO> items, ShippingAddressDto shippingAddress,Long shippingAddressId) {
        this.orderId = orderId;
        this.razorpayOrderId = razorpayOrderId;
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
        this.shippingAddressId = shippingAddressId;
    }
*/
    public OrderDto(String orderId, String razorpayOrderId, String customerId, String orderStatus, Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt, Integer attempts, String receipt, List<PaymentDto> payments, List<OrderItemDTO> items, ShippingAddressDto shippingAddress,Long shippingAddressId) {
   this.orderId=orderId;
   this.razorpayOrderId=razorpayOrderId;
   this.customerId=customerId;
   this.orderStatus=orderStatus;
   this.totalAmount=totalAmount;
   this.createdAt=createdAt;
   this.updatedAt=updatedAt;
   this.attempts=attempts;
   this.receipt=receipt;
   this.payments=payments;
   this.items=items;
   this.shippingAddress=shippingAddress;
   this.shippingAddressId=shippingAddressId;

    }

    public OrderDto(String orderId, String razorpayOrderId, String customerId, String orderStatus, Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt, Integer attempts, String receipt, List<PaymentDto> payments, List<OrderItemDTO> items, ShippingAddressDto shippingAddress) {
        this.orderId=orderId;
        this.razorpayOrderId=razorpayOrderId;
        this.customerId=customerId;
        this.orderStatus=orderStatus;
        this.totalAmount=totalAmount;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.attempts=attempts;
        this.receipt=receipt;
        this.payments=payments;
        this.items=items;
        this.shippingAddress=shippingAddress;
    }
    // Constructor that accepts Order + history
    public OrderDto(Order order, List<OrderHistory> history) {
        this.orderId = order.getOrderId();
        this.razorpayOrderId = order.getRazorpayOrderId();
        this.customerId = order.getCustomerId();
        this.orderStatus = order.getOrderStatus();
        this.totalAmount = order.getTotalAmount();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.attempts = order.getAttempts();
        this.receipt = order.getReceipt();
        this.payments = order.getPayments().stream()
                .map(PaymentDto::fromEntity)
                .toList();

        this.items = order.getItems().stream()
                .map(OrderItemDTO::fromEntity)
                .toList();

        this.shippingAddress = ShippingAddressDto.fromEntity(order.getShippingAddress());

        this.timeline = history.stream()
                .map(OrderHistoryDto::fromEntity)
                .toList();
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
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
    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
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
        dto.setRazorpayOrderId(order.getRazorpayOrderId());
        dto.setCustomerId(order.getCustomerId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setShippingAddressId(
                order.getShippingAddress() != null ? order.getShippingAddress().getId() : null
        );
        dto.setShippingAddress(
                order.getShippingAddress() != null ? ShippingAddressDto.fromEntity(order.getShippingAddress()) : null
        );


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
    public static Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
        order.setRazorpayOrderId(dto.getRazorpayOrderId());
        order.setCustomerId(dto.getCustomerId());
        order.setOrderStatus(dto.getOrderStatus());
        order.setTotalAmount(dto.getTotalAmount());
        order.setCreatedAt(dto.getCreatedAt());
        order.setUpdatedAt(dto.getUpdatedAt());
        order.setAttempts(dto.getAttempts());
        order.setReceipt(dto.getReceipt());

        // Attach shipping address by ID only
        if (dto.getShippingAddressId() != null) {
            ShippingAddressEntity address = new ShippingAddressEntity();
            address.setId(dto.getShippingAddressId());
            order.setShippingAddress(address);
        }

        return order;
    }



}
