package com.thoughtprocessing.service;

import com.thoughtprocessing.controller.ProdOfferController;
import com.thoughtprocessing.dto.ProductOfferDto;
import com.thoughtprocessing.model.ProductOfferEntity;
import com.thoughtprocessing.repository.ProdOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

@Service
public class ProdOfferService {
    private  final Map<String,Offer> currentOfferdetails=new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ProdOfferService.class);


    @Autowired
    private ProdOfferRepository prodOfferRepository;

    public ProdOfferService(){
        currentOfferdetails.put("Mobile Phone",new Offer("Festival Offer", 20.0));
        //currentOfferdetails.put("email Address",new Offer("Festival Offer", 20.0));
        currentOfferdetails.put("Shoes",new Offer("Weekend Sale", 15.0));
        currentOfferdetails.put("Laptop",new Offer("New Year Deal", 25.0));
    }
    public List<ProductOfferDto> getProductOffers(double originalPrice) {
        List<ProductOfferDto> prodList=new ArrayList<>();
        for(Map.Entry<String,Offer> entry:currentOfferdetails.entrySet())
        {
            prodList.add(new ProductOfferDto(entry.getKey(),entry.getValue(),originalPrice));
        }
        return prodList;
    }
    public Optional<ProductOfferDto> getOfferForProduct(String productName,double originalPrice) {
        Offer offer=currentOfferdetails.get(productName);
        if(offer!=null)
        {
            return Optional.of(new ProductOfferDto(productName,offer,originalPrice));
        }
        else
        {
            return Optional.empty();
        }
    }


     public ProductOfferEntity saveOffer(ProductOfferDto dto) {
        ProductOfferEntity saveOffer=new ProductOfferEntity();
        saveOffer.setProductName(dto.getProductName());
        saveOffer.setDescription(dto.getDescription());
        saveOffer.setOriginalPrice(dto.getOriginalPrice());
        saveOffer.setDiscountPercentage(dto.getDiscountPercentage());
         double appliedPrice = Math.max(0, dto.getOriginalPrice() -
                 (dto.getOriginalPrice() * dto.getDiscountPercentage() / 100));


         saveOffer.setAppliedPrice(appliedPrice);



        return prodOfferRepository.save(saveOffer);
    }

    public List<ProductOfferDto> calculateOffers(List<ProductOfferDto> products) {
      List<ProductOfferDto> prodList=new ArrayList<>();
       for(ProductOfferDto dto:products)
       {
               Offer offer=getOfferBYProductName(dto.getProductName());
           if (offer == null) {
               log.warn("No offer found for product: {}", dto.getProductName());
               continue; // skip this item
           }


           double appliedPrice= dto.getOriginalPrice()-(dto.getOriginalPrice() * offer.getDiscountPercentage() / 100);

             dto.setDescription(offer.getDescription());
             dto.setDiscountPercentage(offer.getDiscountPercentage());
             dto.setAppliedPrice(appliedPrice);
             prodList.add(dto);
       }
         return prodList;



}

    private Offer getOfferBYProductName(String productName) {
        return currentOfferdetails.get(productName);
    }
    }
