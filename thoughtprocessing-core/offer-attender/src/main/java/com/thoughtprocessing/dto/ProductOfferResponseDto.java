package com.thoughtprocessing.dto;

public class ProductOfferResponseDto {
    private Long id;
    private String productName;
    private String modelName;
    private String productDescription;   // ✅ add this

    private String offerDescription;
    private Double appliedPrice;
    private Double basePrice;          // ✅ original price
    private Double discountPercentage; // ✅ % off
    private String category;   // ✅ new field
    public ProductOfferResponseDto(){}

    public ProductOfferResponseDto(Long id, String productName,String modelName, String productDescription,
                                   String offerDescription, Double basePrice,
                                   Double appliedPrice, Double discountPercentage,String category) {
        this.id = id;
        this.productName = productName;
        this.modelName = modelName;
        this.productDescription = productDescription;
        this.offerDescription = offerDescription;
        this.basePrice = basePrice;
        this.appliedPrice = appliedPrice;
        this.discountPercentage = discountPercentage;
        this.category = category;
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

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public Double getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(Double appliedPrice) {
        this.appliedPrice = appliedPrice;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
