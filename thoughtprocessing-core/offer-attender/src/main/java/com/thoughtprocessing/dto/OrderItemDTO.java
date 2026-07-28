package com.thoughtprocessing.dto;

public class OrderItemDTO {
    private String productId;
    private String productName;
    private String modelName;
    private String description;
    private String imageUrl;
    private Integer quantity;
    private Long price;
    private Long basePrice;
    private Long appliedPrice;
    private Long discountPercentage;
    private String offerId;
    public OrderItemDTO(){}


    public OrderItemDTO(String productId, String productName, String modelName, String description, String imageUrl, Integer quantity, Long basePrice, Long appliedPrice, Long discountPercentage, String offerId) {
    this.productId = productId;
    this.productName = productName;
    this.modelName = modelName;
    this.description = description;
    this.imageUrl = imageUrl;
    this.quantity = quantity;
    this.price=appliedPrice; //updated price
    this.basePrice = basePrice;
    this.appliedPrice = appliedPrice;
    this.discountPercentage = discountPercentage;
    this.offerId = offerId;

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
    public Long getPrice() {
        return appliedPrice; // or basePrice depending on business logic
    }
    public void setPrice(Long price) {
        this.price = price;
        this.appliedPrice = price; // keep them in sync if needed
    }
}
