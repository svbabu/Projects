package com.thoughtprocessing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_offer",uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "offer_id"}))
public class ProductOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="applied_price")
    private Double appliedPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer;

    public ProductOfferEntity() {}

    public ProductOfferEntity(ProductEntity product, OfferEntity offer, Double appliedPrice) {
        this.product = product;
        this.offer = offer;
        this.appliedPrice = appliedPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(Double appliedPrice) {
        this.appliedPrice = appliedPrice;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public void setOffer(OfferEntity offer) {
        this.offer = offer;
    }
// getters and setters
}
