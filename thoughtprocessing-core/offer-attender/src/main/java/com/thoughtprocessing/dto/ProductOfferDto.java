package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.ProductOfferEntity;
import com.thoughtprocessing.service.Offer;

public class ProductOfferDto {
    private String productName;
    private String description; // matches Offer.description
    private Double originalPrice;
    private Double discountPercentage;
    private Double appliedPrice;
    private Offer  offer;




    public ProductOfferDto() {}

    public ProductOfferDto(String productName, Offer offer, Double originalPrice) {
        this.productName = productName;
        this.offer = offer; // ✅ This was missing
        this.description = offer.getDescription();
        this.discountPercentage = offer.getDiscountPercentage();
        this.originalPrice = originalPrice;
        this.appliedPrice = Math.max(0, originalPrice - (originalPrice * discountPercentage / 100));



        //this.appliedPrice = originalPrice - (originalPrice * offer.discountPercentage / 100);
    }

    public ProductOfferDto(ProductOfferEntity saved) {
        this.productName = saved.getProductName();
        this.description = saved.getDescription();
        this.originalPrice = saved.getOriginalPrice(); // ✅ Now it's defined
        this.discountPercentage =saved.getDiscountPercentage();

        this.appliedPrice = saved.getAppliedPrice(); // 🌿 Use persisted value

        //this.appliedPrice = originalPrice - (originalPrice * discountPercentage / 100);
        //this.appliedPrice = Math.max(0, originalPrice - (originalPrice * discountPercentage / 100));



    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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


    @Override
    public String toString() {
        return "ProductOfferDto{" +
                "productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", originalPrice=" + originalPrice +
                ", appliedPrice=" + appliedPrice +
                ", offer=" + offer +


                '}';
    }


    public Offer getOffer() {
        return offer;
    }
    public void setOffer(Offer offer) {
        this.offer = offer;
    }


}
