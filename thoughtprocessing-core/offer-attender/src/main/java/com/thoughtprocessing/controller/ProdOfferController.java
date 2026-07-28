package com.thoughtprocessing.controller;

//import com.thoughtprocessing.dto.ProductOfferDto;
import com.thoughtprocessing.dto.ProductOfferRequestDto;
import com.thoughtprocessing.dto.ProductOfferResponseDto;
import com.thoughtprocessing.model.ProductOfferEntity;
import com.thoughtprocessing.service.Offer;
import com.thoughtprocessing.service.ProdOfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prod-offers")
@CrossOrigin(origins = "http://localhost:8080")
public class ProdOfferController {

    private final ProdOfferService prodOfferService;

    public ProdOfferController(ProdOfferService prodOfferService) {
        this.prodOfferService = prodOfferService;
    }

    @GetMapping
    public List<ProductOfferResponseDto> getAllOffers(@RequestParam Double appliedPrice) {
        return prodOfferService.getProductOffers(appliedPrice);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductOfferResponseDto> getOffer(
            @PathVariable String productName,
            @RequestParam Double appliedPrice) {
        return prodOfferService.getOfferForProduct(productName, appliedPrice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveOffer(@RequestBody ProductOfferRequestDto dto) {
        ProductOfferResponseDto responseDto = prodOfferService.saveOffer(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Form is saved successfully");
        response.put("offer", responseDto);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{productName}/applied-prices")
    public ResponseEntity<List<Double>> getAppliedPrices(
            @PathVariable String productName) {
        List<Double> prices = prodOfferService.getAppliedPricesForProduct(productName);
        if (prices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prices);
    }
    @PostMapping("/calculate")
    public ResponseEntity<List<ProductOfferResponseDto>> calculateOffers(
            @RequestBody List<ProductOfferRequestDto> requests) {
        return ResponseEntity.ok(prodOfferService.calculateOffers(requests));
    }
    @GetMapping("/category/{categoryName}/offer/{offerId}")
    public List<ProductOfferResponseDto> getOffersByCategory(
            @PathVariable String categoryName,
            @PathVariable Long offerId) {
        return prodOfferService.calculateOffersByCategory(categoryName, offerId);
    }
}
