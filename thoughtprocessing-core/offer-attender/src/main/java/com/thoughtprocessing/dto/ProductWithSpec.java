package com.thoughtprocessing.dto;

public class ProductWithSpec {
    private Long id;
    private String modelName;
    private String brand;
    private Double basePrice;
    private String description;
    private String imageUrl;
    private String ramSize;
    private String processor;
    private String storageCapacity;
    private String operatingSystem;
    private String videoCard;
    private String display;
    private String color;
    public ProductWithSpec() {}
    public ProductWithSpec(Long id, String modelName, String brand, Double basePrice,
                            String description, String imageUrl, String ramSize, String processor,
                            String storageCapacity, String operatingSystem, String videoCard,
                            String display, String color) {
        this.id = id;
        this.modelName = modelName;
        this.brand = brand;
        this.basePrice = basePrice;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ramSize = ramSize;
        this.processor = processor;
        this.storageCapacity = storageCapacity;
        this.operatingSystem = operatingSystem;
        this.videoCard = videoCard;
        this.display = display;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(String videoCard) {
        this.videoCard = videoCard;
    }
}
