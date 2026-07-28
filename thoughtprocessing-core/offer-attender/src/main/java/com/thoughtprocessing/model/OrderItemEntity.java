package com.thoughtprocessing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}) )
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String  productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "base_price")
    private Long basePrice;

    @Column(name = "applied_price")
    private Long appliedPrice;

    @Column(name = "discount_percentage")
    private Long discountPercentage;

    @Column(name = "offer_id")
    private String offerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
     public OrderItemEntity(){}
    public OrderItemEntity(Long id, String productId, String productName, String modelName, String description, String imageUrl, Integer quantity, Long price, Long basePrice, Long appliedPrice, Long discountPercentage, String offerId, Order order) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.modelName = modelName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = appliedPrice;
        this.basePrice = basePrice;
        this.appliedPrice = appliedPrice;
        this.discountPercentage = discountPercentage;
        this.offerId = offerId;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /*public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }*/

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Long getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(Long appliedPrice) {

        this.appliedPrice = appliedPrice;
        this.price = appliedPrice;
    }

    public Long getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Long discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public void setPrice(Long price) {
        this.price = price;
        this.appliedPrice = price; // keep consistent
    }
    public Long getPrice() {
        return appliedPrice; // always return effective price
    }
}

