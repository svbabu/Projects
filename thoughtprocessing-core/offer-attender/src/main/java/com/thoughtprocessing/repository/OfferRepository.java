package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
}
