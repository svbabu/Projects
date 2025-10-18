package com.thoughtprocessing.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductPage {

    private static final Map<String, Offer> currentDetails = new HashMap();
    static {
        currentDetails.put("mobile Phone", new Offer("festival Offer", 20.0));
        currentDetails.put("Shoes", new Offer("Weekend Sale", 15.0));

    }
    public static void showOffer(String ProductName) {
        //Offer offer=currentDetails.get(ProductName);
        //if(offer!=null){
        // System.out.println(ProductName+" "+ offer.description+" -"+offer.discountPercentage+"%off");



        //}
        //else{
        //   System.out.println(ProductName+" No Offer");

        //}
        Optional<Offer> optionalOffer = Optional.ofNullable(currentDetails.get(ProductName));
        optionalOffer.ifPresentOrElse(offer -> System.out.println(offer.getFormattedOffer(ProductName)),
                () -> System.out.println(ProductName + " - No Offer"));


    }
}
