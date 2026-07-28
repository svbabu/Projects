package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, String> {
    //@Query("SELECT o FROM OrderEntity o JOIN FETCH o.items WHERE o.orderId = :orderId")
   // Optional<OrderEntity> findOrderWithItems(@Param("orderId") String orderId);

    /*@Query("SELECT DISTINCT o FROM OrderEntity o LEFT JOIN FETCH o.items WHERE o.orderId = :orderId")
    Optional<OrderEntity> findOrderWithItems(@Param("orderId") String orderId);*/
    @Query("SELECT DISTINCT o FROM OrderEntity o LEFT JOIN FETCH o.items WHERE o.orderId = :orderId")
    List<OrderEntity> findOrderWithItems(@Param("orderId") String orderId);

    @Query("SELECT DISTINCT o FROM OrderEntity o LEFT JOIN FETCH o.items WHERE o.customerId = :customerId")
    List<OrderEntity> findOrdersWithItemsByCustomerId(@Param("customerId") String customerId);
    List<OrderEntity> findByCustomerId(String customerId);
}