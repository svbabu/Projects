package com.thoughtprocessing.service;

import com.thoughtprocessing.enums.OfferMode;
import com.thoughtprocessing.utils.OfferUtils;

import java.util.HashMap;
import java.util.Map;

public class OfferValidatorService {
    public static boolean isValidOffer(OfferMode offer, String productName, double price, double discountPrice){
        return OfferUtils.validProductName(productName)
                && OfferUtils.validProductPrice(price)
                && OfferUtils.validDiscountPrice(price,discountPrice)
                && offer!=null; // optional: validate offer type presence
    }
    public static String isValidOfferSummary(String product, double price, double discountPercentage) {
        if (OfferUtils.validProductName(product) || OfferUtils.validProductPrice(price) || OfferUtils.validDiscountPrice(price, discountPercentage)) {
            return "valid  Offer Details";
        }
        return "invalid  Offer Details";
    }

    public static Map<OfferMode,Boolean> evaluateOfferModes(String productName, double price, double discountPrice)
    {
        Map<OfferMode,Boolean> results=new HashMap<OfferMode,Boolean>();
        for(OfferMode offerMode:OfferMode.values())
        {
            boolean isValidOffer=isValidOffer(offerMode,productName,price,discountPrice);
            results.put(offerMode,isValidOffer);
        }
        return results;
    }




}
