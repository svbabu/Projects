package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.OfferEntity;
import com.thoughtprocessing.model.ProductEntity;
import com.thoughtprocessing.model.ProductOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdOfferRepository extends JpaRepository<ProductOfferEntity, Long> {
    List<ProductOfferEntity> findByAppliedPrice(Double appliedPrice);
    Optional<ProductOfferEntity> findByProduct_NameAndAppliedPrice(String productName, Double appliedPrice);

    Optional<ProductOfferEntity> findByProductAndOffer(ProductEntity product, OfferEntity offer);
}
