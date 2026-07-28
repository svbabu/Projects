package com.thoughtprocessing.repository;
import com.thoughtprocessing.dto.ProductWithSpecs;
import com.thoughtprocessing.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByNameAndBasePrice(String name, Double basePrice);
    List<ProductEntity> findByCategory(String categoryName);
    @Query(value = "SELECT p.id as id, p.model_name as modelName, p.brand as brand, " +
            "p.base_price as basePrice, p.description as description, p.image_url as imageUrl, " +
            "ls.ram_size as ramSize, ls.processor as processor, ls.storage_capacity as storageCapacity, " +
            "ls.operating_system as operatingSystem, ls.video_card as videoCard, " +
            "ls.display as display, ls.color as color " +
            "FROM products p LEFT JOIN laptop_specs ls ON p.id = ls.product_id",
            nativeQuery = true)
        List<ProductWithSpecs> findAllProductsWithSpecs();
}

