package com.thoughtprocessing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;   // primary key

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "attempts")
    private Integer attempts;

    @Column(name = "receipt")
    private String receipt;

    // One order can have many payments
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Payment> payments;
    // One order can have many items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItemEntity> items = new ArrayList<>();

    // One order has one shipping address
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private ShippingAddressEntity shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderHistory> timeline = new ArrayList<>();

    public Order() {}
    public Order(String orderId, String customerId, String orderStatus, Long totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt, Integer attempts, String receipt, List<Payment> payments, List<OrderItemEntity> items,
                 ShippingAddressEntity shippingAddress) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.attempts = attempts;
        this.receipt = receipt;
        this.payments = (payments != null) ? payments : new ArrayList<>();
        this.items = (items != null) ? items : new ArrayList<>();
        this.shippingAddress = shippingAddress;




    }
    /*public Order(List<OrderHistory> timeline)
    {
        this.timeline = (timeline != null) ? timeline : new ArrayList<>();
    }*/

    public Order(String orderId) {
        this.orderId = orderId;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setOrder(this); // maintain bidirectional link
        }

    public void removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setOrder(null); // break the link safely
        }
    // --- Items helpers ---
    public void addItem(OrderItemEntity item) {
        this.items.add(item);
        item.setOrder(this); // maintain bidirectional link
    }

    public List<OrderHistory> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<OrderHistory> timeline) {
        this.timeline = timeline;
    }
    /*public void removeItem(OrderItemEntity item) {
        this.items.remove(item);
        item.setOrder(null); // break the link safely
    }*/

    // --- Shipping helpers ---
  /* public void setShippingAddress(ShippingAddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
        if (shippingAddress != null) {
            shippingAddress.setOrder(this); // maintain bidirectional link if you add back-reference
        }
    }
*/

    public List<OrderItemEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }

    public ShippingAddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void addTimelineEntry(OrderHistory entry) {
        this.timeline.add(entry);
        entry.setOrder(this); // maintain bidirectional link
    }
    public void removeTimelineEntry(OrderHistory entry) {
        this.timeline.remove(entry);
        entry.setOrder(null);
    }

}

