package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByOrder(Order order);
    List<OrderHistory> findByOrderOrderIdOrderByStatusTime(String orderId);
}
