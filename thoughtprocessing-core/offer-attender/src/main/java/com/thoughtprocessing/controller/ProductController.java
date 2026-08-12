package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.ProductDto;
import com.thoughtprocessing.dto.ProductWithSpecs;
import com.thoughtprocessing.repository.ProductRepository;
import com.thoughtprocessing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@CrossOrigin("origins=http://localhost:8080")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository ;

    @Autowired
    public ProductController(ProductService productService,ProductRepository productRepository) {
        
        this.productService = productService;
        this.productRepository=productRepository;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto dto) {
        return productService.saveProduct(dto);
    }

    @GetMapping("/with-specs")
    public List<ProductWithSpecs> getProductsWithSpecs() {
        return productRepository.findAllProductsWithSpecs();
    }

    /*@GetMapping("/with-specs")
    public List<ProductWithSpecs> getAllProductsWithSpecs() {

        return productService.getAllProductsWithSpecs();
    }*/
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
