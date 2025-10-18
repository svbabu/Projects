package com.thoughtprocessing.service;

import com.thoughtprocessing.enums.HomeCenterShop;
import com.thoughtprocessing.enums.OfferMode;

public class OfferEngine {
     /*public void applyAllOffers(String productName,double originalPrice) {
        System.out.println("ProductName:" + productName);
        System.out.println("OriginalPrice:R" + originalPrice);
        for (OfferMode mode : OfferMode.values()) {
            System.out.println("Offer Type: " + mode.name());
            System.out.println(mode.applyOffer(originalPrice));
            System.out.println(".......................");
        }
    }*/

    public String applyAllOffers(String productName, double price) {
        StringBuilder result=new StringBuilder();
        System.out.println("..........................");
        result.append("Product :").append(productName).append("\n");
        for(OfferMode mode: OfferMode.values()){

            result.append("Offer Type:").append(mode.name()).append("\n");
            result.append("Applied Price:").append(mode.applyOffer(price)).append("\n");
            //result.append("..............................");
        }
        result.append("!!End of the Offer Description:"+"\n"+"...............");
        return  result.toString();
    }

    //close logic here
    public String applyHmeCntrAllItems(String code, String desc) {

        StringBuilder result=new StringBuilder();
        result.append("!!Start the Home Center App By Product Items: "+"\n"+"---------------An idea about the Product One with description!"+"\n");
        result.append(" Product Item Code :").append(code).append("\n");
        result.append(" Product Item Description :").append(desc).append("\n");
        result.append("---------------------------------------------------"+"\n");
        result.append("Available Product Items:"+"\n");
        result.append("----------------------------------"+"\n");
        //.append(code).append("\n");

        for (HomeCenterShop shop : HomeCenterShop.values()) {
            result.append(shop.toString()).append("\n");
            result.append("Product :").append(shop.name()).append("\n");
            result.append("Code:").append(shop.getCode()).append("\n");
            result.append("Description :").append(shop.getDescription()).append("\n").append("--------------------------------------"+"\n");

            //System.out.println("Product :"+shop.name()+"\n");
        }

        return  result.toString();
    }
}
