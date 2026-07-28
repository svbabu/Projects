package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.OfferDto;
import com.thoughtprocessing.service.OfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "http://localhost:8080")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferDto> getAllOffers() {
        return offerService.getAllOffers();
    }

    @PostMapping
    public OfferDto createOffer(@RequestBody OfferDto dto) {
        return offerService.createOffer(dto);
    }
}
