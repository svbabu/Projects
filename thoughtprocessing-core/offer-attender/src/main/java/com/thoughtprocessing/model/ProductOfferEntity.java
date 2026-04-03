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
    private Double originalPrice;
    private Double discountPercentage;
    private Double appliedPrice;
    // ✅ Required by JPA
    public ProductOfferEntity() {}

    // Optional convenience constructor
    public ProductOfferEntity(String productName, String description, Double discountPercentage, Double appliedPrice,Double originalPrice) {
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

    public Double getDiscountPercentage() {

        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {

        this.discountPercentage = discountPercentage;
    }
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {

        this.originalPrice = originalPrice;
    }

    public Double getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(Double appliedPrice) {
        this.appliedPrice = appliedPrice;
    }

}
