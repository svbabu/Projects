package com.thoughtprocessing.service;

import com.thoughtprocessing.controller.ProdOfferController;
//import com.thoughtprocessing.dto.ProductOfferDto;
import com.thoughtprocessing.dto.ProductOfferRequestDto;
import com.thoughtprocessing.dto.ProductOfferResponseDto;
import com.thoughtprocessing.model.OfferEntity;
import com.thoughtprocessing.model.ProductEntity;
import com.thoughtprocessing.model.ProductOfferEntity;
import com.thoughtprocessing.repository.OfferRepository;
import com.thoughtprocessing.repository.ProdOfferRepository;
import com.thoughtprocessing.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;

import static org.json.XMLTokener.entity;


@Service
public class ProdOfferService {
    /*private  final Map<String,Offer> currentOfferdetails=new HashMap<>();*/
    private final List<ProductOfferResponseDto> currentOfferdetails = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(ProdOfferService.class);

  private final  ProductRepository productRepository;
  private final  OfferRepository offerRepository;
  private final ProdOfferRepository prodOfferRepository;

  @Autowired
    public ProdOfferService(ProductRepository productRepository, OfferRepository offerRepository, ProdOfferRepository prodOfferRepository) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.prodOfferRepository = prodOfferRepository;
        System.out.println("ProdOfferService constructor called");
        log.info("ProdOfferService initialized with {} offers", currentOfferdetails.size());


    }

    public List<ProductOfferResponseDto> getProductOffers(Double appliedPrice) {
        return prodOfferRepository.findByAppliedPrice(appliedPrice).stream()
                .map(entity -> new ProductOfferResponseDto(
                        entity.getId(),
                        entity.getProduct().getName(),
                        entity.getProduct().getModelName(),
                        entity.getProduct().getDescription(),
                        entity.getOffer().getDescription(),
                        entity.getProduct().getBasePrice(),
                        entity.getAppliedPrice(),
                        entity.getOffer().getDiscountPercentage(),
                        entity.getProduct().getCategory()


                ))
                .collect(Collectors.toList());
    }


    public Optional<ProductOfferResponseDto> getOfferForProduct(String productName, Double appliedPrice) {
        return prodOfferRepository.findAll().stream()
                .filter(entity -> entity.getProduct().getName().equals(productName)
                        && Double.compare(entity.getAppliedPrice(), appliedPrice) == 0)
                .findFirst()
                .map(entity -> new ProductOfferResponseDto(
                        entity.getId(),
                        entity.getProduct().getName(),
                        entity.getProduct().getModelName(),
                        entity.getProduct().getDescription(),
                        entity.getOffer().getDescription(),
                        entity.getProduct().getBasePrice(),
                        entity.getAppliedPrice(),
                        entity.getOffer().getDiscountPercentage(),
                        entity.getProduct().getCategory()

                ));
    }
    /*public Optional<ProductOfferResponseDto> getOfferForProduct(String productName, Double originalPrice) {
        return prodOfferRepository.findByProduct_NameAndAppliedPrice(productName, originalPrice)
                .map(entity -> new ProductOfferResponseDto(
                        entity.getId(),
                        entity.getProduct().getName(),
                        entity.getOffer().getDescription(),
                        entity.getAppliedPrice()
                ));
    }*/



    public List<ProductOfferResponseDto> getOfferByProductName(String productName) {
        return prodOfferRepository.findAll().stream()
                .filter(entity -> entity.getProduct().getName().equals(productName))
                .map(entity -> new ProductOfferResponseDto(
                        entity.getId(),
                        entity.getProduct().getName(),
                        entity.getProduct().getModelName(),
                        entity.getProduct().getDescription(),
                        entity.getOffer().getDescription(),
                        entity.getProduct().getBasePrice(),
                        entity.getAppliedPrice(),
                        entity.getOffer().getDiscountPercentage(),
                        entity.getProduct().getCategory()
                ))
                .collect(Collectors.toList());
    }
    private Double calculateAppliedPrice(ProductEntity product, OfferEntity offer) {
        if (product == null || offer == null) {
            return null; // gracefully handle missing inputs
        }
        return Math.max(0, product.getBasePrice() -
                (product.getBasePrice() * offer.getDiscountPercentage() / 100));
    }


    public ProductOfferResponseDto saveOffer(ProductOfferRequestDto dto) {
        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        OfferEntity offer = offerRepository.findById(dto.getOfferId())
                .orElseThrow(() -> new RuntimeException("Offer not found"));
// Check if already exists
        Optional<ProductOfferEntity> existing = prodOfferRepository
                .findByProductAndOffer(product, offer);

        if (existing.isPresent()) {
            throw new RuntimeException("Offer already exists for this product");
        }
        double appliedPrice = Math.max(0, product.getBasePrice() -
                (product.getBasePrice() * offer.getDiscountPercentage() / 100));

        ProductOfferEntity savedEntity = prodOfferRepository.save(
                new ProductOfferEntity(product, offer, appliedPrice)
        );

        return new ProductOfferResponseDto(
                savedEntity.getId(),
                savedEntity.getProduct().getName(),
                savedEntity.getProduct().getModelName(),
                savedEntity.getProduct().getDescription(),
                savedEntity.getOffer().getDescription(),
                savedEntity.getProduct().getBasePrice(),
                savedEntity.getAppliedPrice(),
                savedEntity.getOffer().getDiscountPercentage(),
                savedEntity.getProduct().getCategory()

        );
    }


    public List<ProductOfferResponseDto> calculateOffers(List<ProductOfferRequestDto> requests) {
        return requests.stream()
                .map(request -> {
                    ProductEntity product = productRepository.findById(request.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    OfferEntity offer = offerRepository.findById(request.getOfferId())
                            .orElseThrow(() -> new RuntimeException("Offer not found"));

                    log.info("Incoming product: {} at base price {}", product.getName(), product.getBasePrice());

                    Double appliedPrice = null;
                    if (product.getBasePrice() != null && offer.getDiscountPercentage() != null) {
                        appliedPrice = Math.max(0, product.getBasePrice() -
                                (product.getBasePrice() * offer.getDiscountPercentage() / 100));
                    } else {
                        log.warn("Base price or discount is null for product {} with offer {}",
                                product != null ? product.getName() : "unknown",
                                offer != null ? offer.getDescription() : "unknown");
                    }
                    log.info("Applied offer {} with discount {}% for product {}",
                            offer.getDescription(), offer.getDiscountPercentage(), product.getName());

                    return new ProductOfferResponseDto(
                            product.getId(),
                            product.getName(),
                            product.getModelName(),
                            product.getDescription(),
                            offer.getDescription(),
                            product.getBasePrice(),
                            appliedPrice,                 // appliedPrice first
                             offer.getDiscountPercentage(),
                            product.getCategory()
                    );

                })
                .collect(Collectors.toList());
    }

    public List<Double> getAppliedPricesForProduct(String productName) {
        return prodOfferRepository.findAll().stream()
                .filter(entity -> entity.getProduct().getName().equals(productName))
                .map(ProductOfferEntity::getAppliedPrice)
                .collect(Collectors.toList());
    }

    public List<ProductOfferResponseDto> calculateOffersByCategory(String categoryName, Long offerId) {
        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        List<ProductEntity> products = productRepository.findByCategory(categoryName);

        return products.stream()
                .map(product -> {
                    Double appliedPrice = Math.max(0, product.getBasePrice() -
                            (product.getBasePrice() * offer.getDiscountPercentage() / 100));

                    return new ProductOfferResponseDto(
                            product.getId(),
                            product.getName(),
                            product.getModelName(),
                            product.getDescription(),       // ✅ productDescription
                            offer.getDescription(),         // offer label
                            product.getBasePrice(),
                            appliedPrice,
                            offer.getDiscountPercentage(),
                            product.getCategory()// ✅ category
                    );
                })
                .collect(Collectors.toList());
    }


   /* private Offer getOfferBYProductName(String productName) {
        return currentOfferdetails.get(productName);
    }*/

}