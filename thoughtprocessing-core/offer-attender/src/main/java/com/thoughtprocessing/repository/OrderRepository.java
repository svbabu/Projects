package com.thoughtprocessing.repository;
import com.thoughtprocessing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface OrderRepository extends JpaRepository<Order, String>{
    // Example: find order by receipt
    Optional<Order> findByReceipt(String receipt);
    // Example: find order by customerId
     Optional<Order> findByCustomerId(Long customerId);
     // Example: find order by status
    Optional<Order> findByOrderStatus(String orderStatus);

    Optional<Order> findByOrderId(String razorpayOrderId);
}

