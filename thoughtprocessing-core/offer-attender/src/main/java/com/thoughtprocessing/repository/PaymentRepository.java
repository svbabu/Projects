package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    //Payment findByOrderId(String orderId) ;
    //Payment findByOrder_OrderId(Long order_id);
    Payment findByOrder_OrderId(String order_id);
    Payment findByBank(String bank);
    Payment findByPaymentId(String paymentId);
    // External references (Razorpay)
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);
}
