package com.thoughtprocessing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="description")
    private String description;
    @Column(name="discount_percentage")
    private Double discountPercentage;
public OfferEntity() {}

    public OfferEntity(Long id, String description, Double discountPercentage) {
        this.id = id;
        this.description = description;
        this.discountPercentage = discountPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public Double calculateAppliedPrice(Double originalPrice) {
        return originalPrice - (originalPrice * discountPercentage / 100);
    }

    // getters and setters
}
