package com.thoughtprocessing.service;

public class Offer {
    public String description;

    public double discountPercentage;
    public Offer(String description, double discountPercentage)
    {
        this.description=description;
        this.discountPercentage=discountPercentage;

    }
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormattedOffer(String productName)
    {

        return productName+" "+this.discountPercentage;
    }
}
