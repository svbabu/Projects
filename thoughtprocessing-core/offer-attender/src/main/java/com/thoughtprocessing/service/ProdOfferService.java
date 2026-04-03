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
import java.util.stream.Collectors;
import java.util.Optional;


@Service
public class ProdOfferService {
    /*private  final Map<String,Offer> currentOfferdetails=new HashMap<>();*/
    private final List<ProductOfferDto> currentOfferdetails = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(ProdOfferService.class);


    @Autowired
    private ProdOfferRepository prodOfferRepository;

    public ProdOfferService() {
        System.out.println("ProdOfferService constructor called");


      /*  currentOfferdetails.put("Mobile Phone",new Offer("Festival Offer", 20.0));
        //currentOfferdetails.put("email Address",new Offer("Festival Offer", 20.0));
        currentOfferdetails.put("Shoes",new Offer("Weekend Sale", 15.0));
        currentOfferdetails.put("Laptop",new Offer("New Year Deal", 25.0));
        currentOfferdetails.put("Laptop",new Offer("New Year Deal", 25.0));*/
        //multiple laptops
        // Offer diwaliOffer = new Offer("Diwali Festival Offer", 30.0);
        // Offer diwali_festival_offer=new Offer("Diwali Festival Offer", 30.0);

        currentOfferdetails.add(new ProductOfferDto("Laptop", new Offer("Diwali Festival Offer", 30.0), 40000.0));
        currentOfferdetails.add(new ProductOfferDto("Laptop", new Offer("Diwali Festival Offer", 30.0), 50000.0));
        currentOfferdetails.add(new ProductOfferDto("Laptop", new Offer("Clearance Sale", 35.0), 74000.0));
        currentOfferdetails.add(new ProductOfferDto("Laptop", new Offer("New Year Sale", 25.0), 80000.0));
        currentOfferdetails.add(new ProductOfferDto("Mobile Phone", new Offer("Festival Offer", 20.0), 30000.0));
        currentOfferdetails.add(new ProductOfferDto("Shoes", new Offer("Weekend Sale", 15.0), 2000.0));

        log.info("ProdOfferService initialized with {} offers", currentOfferdetails.size());


    }

    public List<ProductOfferDto> getProductOffers(double originalPrice) {
        /*List<ProductOfferDto> prodList=new ArrayList<>();*/
        List<ProductOfferDto> prodList = new ArrayList<>();
       /* for(Map.Entry<String,Offer> entry:currentOfferdetails.entrySet())
        {
            prodList.add(new ProductOfferDto(entry.getKey(),entry.getValue(),originalPrice));
        }*/
        for (ProductOfferDto dto : currentOfferdetails) {
            if (dto.getOriginalPrice() == originalPrice) {
                prodList.add(dto);
            }
        }


        /* return prodList;*/
        return prodList;
    }

    /*public Optional<ProductOfferDto> getOfferForProduct(String productName,double originalPrice) {
        Offer offer=currentOfferdetails.get(productName);
        if(offer!=null)
        {
            return Optional.of(new ProductOfferDto(productName,offer,originalPrice));
        }
        else
        {
            return Optional.empty();
        }
    }*/
    public Optional<ProductOfferDto> getOfferForProduct(String productName, double originalPrice) {
        return currentOfferdetails.stream()
                .filter(dto -> dto.getProductName().equals(productName) &&
                        dto.getOriginalPrice() == originalPrice)
                .findFirst();
    }

    /*public List<ProductOfferDto> getOffersForProduct(String productName) {
        return currentOfferdetails.stream()
                .filter(dto -> dto.getProductName().equals(productName))
                .collect(Collectors.toList());
    }*/
    private List<ProductOfferDto> getOfferBYProductName(String productName) {
        return currentOfferdetails.stream()
                .filter(dto -> dto.getProductName().equals(productName))
                .collect(Collectors.toList());
    }

    public ProductOfferEntity saveOffer(ProductOfferDto dto) {
        ProductOfferEntity saveOffer = new ProductOfferEntity();
        saveOffer.setProductName(dto.getProductName());
        saveOffer.setDescription(dto.getDescription());
        saveOffer.setOriginalPrice(dto.getOriginalPrice());
        saveOffer.setDiscountPercentage(dto.getDiscountPercentage());
        double appliedPrice = Math.max(0, dto.getOriginalPrice() -
                (dto.getOriginalPrice() * dto.getDiscountPercentage() / 100));


        saveOffer.setAppliedPrice(appliedPrice);


        return prodOfferRepository.save(saveOffer);
    }

    /* public List<ProductOfferDto> calculateOffers(List<ProductOfferDto> products) {
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



 }*/
    public List<ProductOfferDto> calculateOffers(List<ProductOfferDto> products) {
        products.forEach(p -> log.info("Incoming product: {} at price {}", p.getProductName(), p.getOriginalPrice()));


        List<ProductOfferDto> prodList = new ArrayList<>();

        for (ProductOfferDto dto : products) {
            log.info("Processing product: {} at price {}", dto.getProductName(), dto.getOriginalPrice());
            currentOfferdetails.forEach(o -> log.info("Available Offer: {} at price {}, Offer: {}",
                    o.getProductName(), o.getOriginalPrice(), o.getOffer()));


            Optional<ProductOfferDto> matchedOffer = currentOfferdetails.stream()
                    .filter(o -> o.getProductName().equalsIgnoreCase(dto.getProductName())
                            &&o.getOffer() != null
                            && o.getOriginalPrice() != null
                            && dto.getOriginalPrice() != null
                            && Double.compare(o.getOriginalPrice(), dto.getOriginalPrice()) == 0

                    )
                    .findFirst();


            if (matchedOffer.isEmpty() || matchedOffer.get().getOffer() == null) {
                log.warn("No valid offer found for product: {} with price {}", dto.getProductName(), dto.getOriginalPrice(), dto.getOffer());
                //continue;
            }
            log.info("Trying to match: {} at price {}", dto.getProductName(), dto.getOriginalPrice());
            matchedOffer.ifPresentOrElse(offerDto -> {
                Offer offer = offerDto.getOffer();
                if (offer == null) {
                    log.warn("Offer is null for product: {} with price {}", dto.getProductName(), dto.getOriginalPrice());
                    return;
                }

                double appliedPrice = dto.getOriginalPrice() - (dto.getOriginalPrice() * offer.getDiscountPercentage() / 100);
                dto.setDescription(offer.getDescription());
                dto.setDiscountPercentage(offer.getDiscountPercentage());
                dto.setAppliedPrice(appliedPrice);
                dto.setOffer(new Offer(offer.getDescription(), offer.getDiscountPercentage()));
                prodList.add(dto);
            }, () -> {
                log.warn("No valid offer found for product: {} with price {}", dto.getProductName(), dto.getOriginalPrice());
            });


        }

        // Add remaining offers not in the request
       /* for (ProductOfferDto offerDto : currentOfferdetails) {
            boolean alreadyIncluded = products.stream().anyMatch(p ->
                    p.getProductName().equalsIgnoreCase(offerDto.getProductName()) &&
                            Double.compare(p.getOriginalPrice(), offerDto.getOriginalPrice()) == 0
            );

            if (!alreadyIncluded) {
                prodList.add(offerDto);
            }
        }*/

        return prodList;
    }






   /* private Offer getOfferBYProductName(String productName) {
        return currentOfferdetails.get(productName);
    }*/

}