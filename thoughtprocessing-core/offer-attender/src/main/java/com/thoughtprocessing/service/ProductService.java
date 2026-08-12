package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.ProductDto;
import com.thoughtprocessing.dto.ProductWithSpecs;
import com.thoughtprocessing.model.ProductEntity;
import com.thoughtprocessing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Optional<ProductDto> getProduct(String name, Double basePrice) {
        return productRepository.findByNameAndBasePrice(name, basePrice)
                .map(ProductDto::new);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public ProductDto saveProduct(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setBasePrice(dto.getBasePrice());
        entity.setDescription(dto.getDescription());
        // New fields
        entity.setBrand(dto.getBrand());
        entity.setImageUrl(dto.getImageUrl());

        ProductEntity saved = productRepository.save(entity);
        return new ProductDto(saved);
    }
    public List<ProductWithSpecs> getAllProductsWithSpecs() {
        return productRepository.findAllProductsWithSpecs();
    }

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDto dto = new ProductDto();
                    dto.setName(product.getName());
                    dto.setModelName(product.getModelName());
                    dto.setCategory(product.getCategory());
                    dto.setBasePrice(product.getBasePrice());
                    dto.setDescription(product.getDescription());
                    dto.setBrand(product.getBrand());
                    dto.setImageUrl(product.getImageUrl());
                    return dto;
                });
    }


}
