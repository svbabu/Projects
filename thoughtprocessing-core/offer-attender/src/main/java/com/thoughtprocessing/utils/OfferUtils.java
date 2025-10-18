package com.thoughtprocessing.utils;

public class OfferUtils {
    public static boolean validProductName(String productName) {
        //if (productName == null|| productName.isEmpty()){
        //return false;
        //}
        return productName!=null && productName.matches("^[A-Za-z ]+$");

    }
    public static boolean validProductPrice(double price) {
        return price >= 0;
    }

    public static boolean validDiscountPrice(double price,double discountPrice)
    {
        return discountPrice > 0 && discountPrice<=price;
    }
}
