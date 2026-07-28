package com.thoughtprocessing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // works with BIGSERIAL in Postgres
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="model_name")
    private String modelName;
    @Column(name="category")
    private String category;
    @Column(name = "base_price")
    private Double basePrice;
    // Newly added fields
    @Column(name="brand")
    private String brand;
    @Column(name="img_url")
    private String imageUrl;
    @Column(name="stock")
    private Integer stock;

    @Column(name="description")
    private String description;
    public ProductEntity() {}
    public ProductEntity(Long id,String productName,String modelName,String category, Double price,String description) {
        this.id = id;
        this.name = productName;
        this.modelName = modelName;
        this.category = category;
        this.basePrice=price;
        this.description = description;
    }
    public ProductEntity(Long id,String productName,String modelName,String brand,String category, Double price,String description,String imageUrl) {
        this.id = id;
        this.name = productName;
        this.modelName = modelName;
        this.category = category;
        this.basePrice=price;
        this.description = description;
        // New fields
        this.brand = brand;
        this.imageUrl=imageUrl;
    }
    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

