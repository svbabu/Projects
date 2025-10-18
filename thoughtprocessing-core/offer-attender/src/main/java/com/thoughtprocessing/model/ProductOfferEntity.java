package com.thoughtprocessing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_offer")
public class ProductOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String description;
    // 🌿 The missing heartbeat
    private double originalPrice;
    private double discountPercentage;
    private double appliedPrice;
    // ✅ Required by JPA
    public ProductOfferEntity() {}

    // Optional convenience constructor
    public ProductOfferEntity(String productName, String description, double discountPercentage, double appliedPrice,double originalPrice) {
        this.productName = productName;
        this.description = description;
        this.originalPrice = originalPrice;
        this.discountPercentage = discountPercentage;
        this.appliedPrice = appliedPrice;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(double appliedPrice) {
        this.appliedPrice = appliedPrice;
    }

}
