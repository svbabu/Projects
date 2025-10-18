package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.ProductOfferDto;
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
@CrossOrigin("origins=http://localhost:8080")
public class ProdOfferController {

    private final ProdOfferService prodOfferService;
    public ProdOfferController(ProdOfferService prodOfferService) {
        this.prodOfferService = prodOfferService;
    }
    @GetMapping
    public List<ProductOfferDto> getAllOffers(@RequestParam double originalprice)
    {
        return prodOfferService.getProductOffers( originalprice);
    }
    @GetMapping("{ProductName}")
        public ResponseEntity<ProductOfferDto> getOffer(@PathVariable String ProductName,double originalprice)
        {
            return prodOfferService.getOfferForProduct(ProductName,originalprice).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }


    //public ResponseEntity<Product
    // OfferDto>  saveOffer(@RequestBody ProductOfferDto dto)
    //{
    @PostMapping("/save")
        ResponseEntity<Map<String, Object>> saveOffer(@RequestBody ProductOfferDto dto) {
        ProductOfferEntity saved = prodOfferService.saveOffer(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Form is saved successfully");
        response.put("offer", new ProductOfferDto(saved));

        return ResponseEntity.ok(response);
    }
    @PostMapping("/calculate")
    public List<ProductOfferDto> calculateOffers(@RequestBody List<ProductOfferDto> products) {
        return prodOfferService.calculateOffers(products);
    }
}









