package com.thoughtprocessing.dto;

public class OfferDto {

    private Long id;

    private String description;
    private Double discountPercentage;
public OfferDto() {}

    public OfferDto(Long id, String description, Double discountPercentage) {
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

}