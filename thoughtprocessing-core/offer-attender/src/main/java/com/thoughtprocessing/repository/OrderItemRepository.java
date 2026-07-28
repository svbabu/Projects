package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByOrderOrderId(String orderId);
    //boolean existsByOrderIdAndProductId(String orderId, String productId);
}
