package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.OfferDto;
import com.thoughtprocessing.model.OfferEntity;
import com.thoughtprocessing.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<OfferDto> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(entity -> new OfferDto(entity.getId(), entity.getDescription(), entity.getDiscountPercentage()))
                .toList();
    }

    public OfferDto createOffer(OfferDto dto) {
        OfferEntity entity = new OfferEntity();
        entity.setDescription(dto.getDescription());
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        OfferEntity saved = offerRepository.save(entity);
        return new OfferDto(saved.getId(), saved.getDescription(), saved.getDiscountPercentage());
    }
}
