package com.thoughtprocessing.repository;
import com.thoughtprocessing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface OrderRepository extends JpaRepository<Order, String>{
    // Example: find order by receipt
    Optional<Order> findByReceipt(String receipt);
    // Example: find order by customerId
     Optional<Order> findByCustomerId(String customerId);
     // Example: find order by status
    Optional<Order> findByOrderStatus(String orderStatus);

    Optional<Order> findByOrderId(String razorpayOrderId);


    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items WHERE o.orderId = :orderId")
    List<Order> findOrderWithItems(@Param("orderId") String orderId);
}

