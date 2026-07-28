package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.ProductEntity;

public class ProductDto {
    private String name;
    private String modelName;
    private String category;
    private Double basePrice;
    private String description;
    // Newly added fields
    private String brand;
    private String imageUrl;
    private Integer stock;
    public ProductDto() {}

    public ProductDto(ProductEntity entity) {
        this.name = entity.getName();
        this.modelName = entity.getModelName();
        this.category = entity.getCategory();
        this.basePrice = entity.getBasePrice();
        this.description = entity.getDescription();
        // New fields
        this.brand = entity.getBrand();
        this.imageUrl = entity.getImageUrl();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
