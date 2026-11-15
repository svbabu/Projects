package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.OrderDto;
import com.thoughtprocessing.dto.PaymentDto;
import com.thoughtprocessing.dto.RazorpayResponse;
import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderPaymentCoordinatorService {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderPaymentCoordinatorService(OrderService orderService,
                                          PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @Transactional
    public OrderDto createOrderWithPayment(String customerId,
                                           Long amount,
                                           String receipt,
                                           Long shippingAddressId,
                                           String method,        // <-- add here
                                           String bankOrUpiId) throws Exception {
        System.out.println(
                "createOrderWithPayment START"
        );
        // Step 1: Create order (calls Razorpay once inside PaymentService)
        OrderDto orderDto = orderService.createOrderForCustomer(customerId, amount, receipt, shippingAddressId);

        // Step 2: Attach payment to that order
        Order order = orderService.findEntityById(orderDto.getOrderId());

        Payment payment = paymentService.attachPaymentToOrder(order,
                orderDto.getRazorpayOrderId(),
               method,   // e.g. "NETBANKING", "CARD", "UPI"
                bankOrUpiId,     // bank name or UPI ID from request
                amount);

        // Step 3: Optionally update DTO with payment info
        orderDto.setPayments(List.of(PaymentDto.fromEntity(payment)));

        return orderDto;
    }

    @Transactional
    public void finalizePayment(RazorpayResponse response) throws Exception {
        // Just delegate to PaymentService
        paymentService.finalizePayment(response);
    }
   /* @Transactional
    public void finalizePayment(String razorpayOrderId, String status) {
        // Step 1: Update payment
        paymentService.updatePaymentStatus(razorpayOrderId, status);

        // Step 2: Update order status
        orderService.updateOrderStatusByRazorpayId(razorpayOrderId, status);
    }*/
}

