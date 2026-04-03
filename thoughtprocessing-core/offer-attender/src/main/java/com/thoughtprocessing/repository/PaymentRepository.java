package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderId(String orderId) ;

    Payment findByPaymentId(String paymentId);
}
