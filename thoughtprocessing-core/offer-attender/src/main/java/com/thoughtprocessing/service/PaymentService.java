package com.thoughtprocessing.service;

import com.razorpay.Order;

import com.thoughtprocessing.dto.OrderSuccessDTO;
import com.thoughtprocessing.dto.RazorpayResponse;
import com.thoughtprocessing.helper.OrderStatusUpdater;
import com.thoughtprocessing.model.Merchant;
import com.thoughtprocessing.model.OrderHistory;
import com.thoughtprocessing.model.Payment;
import com.thoughtprocessing.model.ShippingAddressEntity;
import com.thoughtprocessing.repository.MerchantRepository;
import com.thoughtprocessing.repository.OrderHistoryRepository;
import com.thoughtprocessing.repository.OrderRepository;
import com.thoughtprocessing.repository.PaymentRepository;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.transaction.annotation.Transactional;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    private  OrderStatusUpdater orderStatusUpdater;
    /*@Autowired
    private OrderHistoryRepository historyRepository; // ✅ inject here*/

    /*@Autowired
    private OrderService orderService;*/


    private RazorpayClient razorpayClient;
    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;  // <-- declare here
    @Value("${razorpay.key}")
    private String key;
    @Value("${razorpay.secret}")
    private String secret;

    public PaymentService(@Value("${razorpay.key}") String key, @Value("${razorpay.secret}") String secret) throws Exception {
        // Initialize with your Test or Live keys
        this.razorpayClient = new RazorpayClient(key, secret);
    }


    public Payment attachPaymentToOrder(com.thoughtprocessing.model.Order order, String razorpayOrderId,String method,String bankOrUpiId, Long amount) {
        System.out.println("attachPaymentToOrder is calling");
        System.out.println(
                "attachPaymentToOrder -> Razorpay OrderId = "
                        + razorpayOrderId
        );
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setRazorpayOrderId(razorpayOrderId);
        payment.setMethod(method); // e.g. "NETBANKING", "CARD", "UPI"
        payment.setBank(bankOrUpiId); // optional, depending on method
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }
    @Transactional
    public Payment savePaymentWithOrder(String checkoutOrderId,String bank, Long amount) throws Exception {
        System.out.println("savePaymentWithOrder is calling");
        // Build Razorpay order request
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        // Create Razorpay order once
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        String razorpayOrderId = razorpayOrder.get("id");
        System.out.println(
                "savePaymentWithOrder -> Razorpay OrderId = "
                        + razorpayOrderId
        );

        Merchant merchant = merchantRepository.findById(101L)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        System.out.println(
                "checkoutOrderId received = " + checkoutOrderId
        );
        com.thoughtprocessing.model.Order order =
                orderRepository.findById(checkoutOrderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found: "
                                                + checkoutOrderId
                                )
                        );
        order.setRazorpayOrderId(razorpayOrderId);
        //order.setOrderStatus("CREATED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        // 🔑 Check if payment already exists
        Optional<Payment> existingPayment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
        Payment payment;
        if (existingPayment.isPresent()) {
            payment = existingPayment.get();
            payment.setBank(bank);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setMethod("NetBanking");
        } else {
            payment = new Payment();
            payment.setBank(bank);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setMethod("netbanking");
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setMerchant(merchant);
            payment.setOrder(order);
        }
        paymentRepository.save(payment);

        return payment;
    }

    @Transactional
   public Payment saveUpiPayment(String checkoutOrderId,String upiId, Long amount) throws Exception {
        System.out.println("Received UPI payment request: " + upiId + " Amount: " + amount);
        // Create Razorpay order
        // Build Razorpay order request
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        // Create Razorpay order once
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        String razorpayOrderId = razorpayOrder.get("id");
        System.out.println(
                "saveUpiPayment -> Razorpay OrderId = "
                        + razorpayOrderId
        );

        Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                .orElseThrow(() -> new RuntimeException("Merchant not found"));
        System.out.println(
                "checkoutOrderId received = " + checkoutOrderId
        );
        com.thoughtprocessing.model.Order order =
                orderRepository.findById(checkoutOrderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found: "
                                                + checkoutOrderId
                                )
                        );
        order.setRazorpayOrderId(razorpayOrderId);
        //order.setOrderStatus("CREATED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        // 🔑 Check if payment already exists
        Optional<Payment> existingPayment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
        Payment payment;
        if (existingPayment.isPresent()) {
            payment = existingPayment.get();
            //payment.setBank(bank);
            payment.setUpiId(upiId);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setMethod("upi");
            payment.setRazorpayOrderId(razorpayOrderId);
        } else {
            payment = new Payment();
            //payment.setBank(bank);
            payment.setUpiId(upiId);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setMethod("upi");
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setMerchant(merchant);
            payment.setOrder(order);
        }
        paymentRepository.save(payment);

        return payment;

    }
    @Transactional
    public Payment saveCardPayment(String checkoutOrderId,Long amount) throws Exception {
        System.out.println("Received Card payment request: Amount=" + amount);
        // Create Razorpay order
        // Build Razorpay order request
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        // Create Razorpay order once
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        String razorpayOrderId = razorpayOrder.get("id");
        System.out.println(
                "saveCardPayment -> Razorpay OrderId = "
                        + razorpayOrderId
        );
        Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        System.out.println(
                "checkoutOrderId received = " + checkoutOrderId
        );
        com.thoughtprocessing.model.Order order =
                orderRepository.findById(checkoutOrderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found: "
                                                + checkoutOrderId
                                )
                        );
        order.setRazorpayOrderId(razorpayOrderId);
        //order.setOrderStatus("CREATED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        // 🔑 Check if payment already exists
        Optional<Payment> existingPayment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
        Payment payment;
        if (existingPayment.isPresent()) {
            payment = existingPayment.get();
            //payment.setBank(bank);
           // payment.setUpiId(upiId);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setMethod("card");
            payment.setRazorpayOrderId(razorpayOrderId);
            logger.info("Card Payment Added Successfully: Amount={}", payment.getAmount());
        } else {
            payment = new Payment();
            //payment.setBank(bank);
            //payment.setUpiId(upiId);
            payment.setAmount(amount);
            payment.setStatus("PENDING");
            payment.setMethod("card");
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setMerchant(merchant);
            payment.setOrder(order);
            logger.info("Card Payment Added Successfully: Amount={}", payment.getAmount());
        }
        paymentRepository.save(payment);

        return payment;
    }
    public String createOrder(int amount) throws Exception {
        System.out.println("createOrder() method called");

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount ); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        // Correct way: use razorpayClient.orders.create(...)
        Order order = razorpayClient.orders.create(orderRequest);
        String orderId = order.get("id");
        System.out.println(
                "createOrder() generated Razorpay OrderId = "
                        + orderId
        );

        return order.toString(); // or return order.get("id")
    }
    @Transactional
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public boolean verifySignature(RazorpayResponse response) {
        try {
            System.out.println("Verify request: orderId=" + response.getRazorpayOrderId() +
                    ", paymentId=" + response.getRazorpayPaymentId() +
                    ", signature=" + response.getSignature());


            String payload = response.getRazorpayOrderId() + "|" + response.getRazorpayPaymentId();
           /* System.out.println("Payload: " + payload);*/
            logger.debug("Payload: {}", payload);


            String actualSignature = response.getSignature();

           // Use env variable in production
            String generatedSignature = hmacSha256(payload, webhookSecret);
            /*System.out.println("Generated Signature: " + generatedSignature);*/
            logger.debug("Generated Signature: {}", generatedSignature);
            return generatedSignature.equals(actualSignature);
        } catch (Exception e) {
           /* e.printStackTrace();*/
            logger.error("Error occurred while verifying signature: " + e.getMessage());
            return false;
        }
    }
    private String hmacSha256(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        System.out.println("HMAC"+sha256_HMAC.getMacLength()+secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hash);
    }
    public String getPaymentStatus(String paymentId) {
        try {
           // RazorpayClient client = new RazorpayClient("rzp_test_key", "secret");
            com.razorpay.Payment payment = razorpayClient.payments.fetch(paymentId);
            // Payment status can be "created", "authorized", "captured", "failed"
            return payment.get("status");
        } catch (RazorpayException e) {
            logger.error("Error fetching payment status for {}: {}", paymentId, e.getMessage(), e);
            return "FAILED";
        }
    }
    public Payment finalizePayment(RazorpayResponse response) throws Exception {
        if (verifySignature(response)) {
            System.out.println("finalizePayment{} method is called");
            // Fetch full payment details from Razorpay
            com.razorpay.Payment razorpayPayment = razorpayClient.payments.fetch(response.getRazorpayPaymentId());
            System.out.println();
            JSONObject upiObj = razorpayPayment.get("upi"); // returns a JSONObject
            String upiId = null;
            if (upiObj != null && upiObj.has("payer_account")) {
                System.out.println(upiObj.get("upiId"));
                upiId = upiObj.getString("payer_account");
            }

            String status = razorpayPayment.get("status"); // authorized, captured, failed
            System.out.println("Payment Status: " + status);
            System.out.println("Step 1");

            //razorpay update
            Payment payment = paymentRepository.findByRazorpayOrderId(response.getRazorpayOrderId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No payment found for Razorpay orderId " + response.getRazorpayOrderId()));
            payment.setRazorpayPaymentId(response.getRazorpayPaymentId()); // external Razorpay paymentId
            payment.setPaymentId(response.getRazorpayPaymentId());
            System.out.println("Step 2");
            payment.setStatus(status);
            System.out.println("Step 3");
            System.out.println("Full Razorpay Payment = " + razorpayPayment);

            System.out.println("Contact = " +
                    razorpayPayment.get("contact"));

            System.out.println("Email = " +
                    razorpayPayment.get("email"));

            System.out.println("Bank = " +
                    razorpayPayment.get("bank"));

            System.out.println("Created At = " +
                    razorpayPayment.get("created_at"));
            System.out.println("A-contact");
            if (razorpayPayment.has("contact")) {
                payment.setContact((String) razorpayPayment.get("contact"));
            }

            System.out.println("B-email");
            if (razorpayPayment.has("email")) {
                payment.setEmail((String) razorpayPayment.get("email"));
            }

            System.out.println("C-bank");
            //if (razorpayPayment.has("bank")) {
               // payment.setBank((String) razorpayPayment.get("bank"));
            ///}
            Object bankObj = razorpayPayment.get("bank");

            System.out.println("bankObj = " + bankObj);

            if (bankObj != null &&
                    bankObj != JSONObject.NULL) {

                payment.setBank(bankObj.toString());
            }

            System.out.println("D-createdAt");
            payment.setCreatedAt(LocalDateTime.now());

            System.out.println("E-upi");
            if (razorpayPayment.has("card")
                    && razorpayPayment.get("card") != JSONObject.NULL) {

                JSONObject card =
                        (JSONObject) razorpayPayment.get("card");

                payment.setCardDetails(
                        "**** **** **** " +
                                card.optString("last4", "")
                );

                payment.setCardNetwork(
                        card.optString("network", "")
                );

                payment.setCardType(
                        card.optString("type", "")
                );

                payment.setIssuer(
                        card.optString("issuer", "")
                );

                payment.setCardSubType(
                        card.optString("sub_type", "")
                );
            }
                 System.out.println("Before payment save");
            paymentRepository.save(payment);


// 🔑 Find existing order by Razorpay orderId
            com.thoughtprocessing.model.Order order = orderRepository.findByRazorpayOrderId(response.getRazorpayOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            order.setOrderStatus("captured".equals(status) ? "PAID" : "PAYMENT_FAILED");
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
            if (response.getCustomerId() != null) {
                System.out.println("CustomerId preserved: " + order.getCustomerId());

                //order.setCustomerId(response.getCustomerId());
            }
            //order.setRazorpayOrderId(response.getOrderId()); // external Razorpay orderId
            //order.setShippingAddressId(response.getShippingAddressId()); // from Order entity has join column with shipping address id so that we cant create it in Order class
            // Preserve existing shipping address
            if (order.getShippingAddress() != null) {
                Long addressId = order.getShippingAddress().getId();
                System.out.println("Shipping address id: " + addressId);

                // Re‑attach the same entity so Hibernate keeps the FK
                order.setShippingAddress(order.getShippingAddress());
            }
            /*if (response.getShippingAddressId() != null) {
                ShippingAddressEntity address = new ShippingAddressEntity();
                address.setId(response.getShippingAddressId());
                order.setShippingAddress(address);
            }*/
           /* order.setRazorpayOrderId(response.getRazorpayOrderId());
            order.setOrderStatus(status.equals("captured") ? "PAID" : "PAYMENT_FAILED");
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);*/
            //order-history status cal
            if ("captured".equals(status)) {
                orderStatusUpdater.updateOrderStatus(order, "PLACED", "Order created by customer");
            } else {
                orderStatusUpdater.updateOrderStatus(order, "PAYMENT_FAILED", "Payment failed");
            }
            // Save history directly
           /* OrderHistory history = new OrderHistory(
                    order,
                    status.equals("captured") ? "PLACED" : "PAYMENT_FAILED",
                    LocalDateTime.now(),
                    status.equals("captured") ? "Order created by customer" : "Payment failed"
            );
            historyRepository.save(history);*/
            Payment savedPayment = paymentRepository.save(payment);

            System.out.println("Saved Contact = " + savedPayment.getContact());
            System.out.println("Saved Email = " + savedPayment.getEmail());
            System.out.println("Saved Bank = " + savedPayment.getBank());
            System.out.println("Saved PaymentId = " + savedPayment.getPaymentId());

            return savedPayment;

           // return paymentRepository.save(payment);
        } else {
            throw new Exception("Signature verification failed");
        }
    }


    public Payment saveCodPayment(Long amount) {
        System.out.println("Received COD payment request: Amount=" + amount);
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        // COD is pending until delivery
        payment.setMethod("COD");
        logger.info("COD Payment Added Successfully: Amount={}",
                payment.getAmount());
        // No Razorpay order creation needed for COD
        return paymentRepository.save(payment);
    }

    public Optional<OrderSuccessDTO> getOrderSuccDetls(String OrderId) {
        logger.info("getOrderSuccDetls: OrderId={}, PaymentId={},Amount={},Status={},Method={},UpiId={}");
        System.out.println(
                "getOrderSuccDetls called with orderId = "
                        + OrderId
        );
        /*  JSONObject paymentEntity = json.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");
        JSONObject acquirerData = paymentEntity.getJSONObject("acquirer_data");*/
        //Payment payment = paymentRepository.findByOrderId(OrderId);
        Payment payment = paymentRepository.findByOrder_OrderId(OrderId);
        System.out.println(
                "Payment found = " + payment
        );
        if (payment != null) {
            System.out.println(
                    "Payment Method = "
                            + payment.getMethod()
            );
            OrderSuccessDTO dto;
            System.out.println("Payment Method = " + payment.getMethod());
            System.out.println(
                    "Payment Method = " + payment.getMethod()
            );

            System.out.println(
                    "RazorpayOrderId = " + payment.getRazorpayOrderId()
            );

            System.out.println(
                    "RazorpayPaymentId = " + payment.getRazorpayPaymentId()
            );
            System.out.println("PaymentId = " + payment.getPaymentId());
            System.out.println("RazorpayPaymentId = " + payment.getRazorpayPaymentId());
            System.out.println("Contact = " + payment.getContact());
            System.out.println("Email = " + payment.getEmail());
            System.out.println("CreatedAt = " + payment.getCreatedAt());
            switch (payment.getMethod().toLowerCase()) {
                case "upi":
            dto=new OrderSuccessDTO(
                    //payment.getOrderId(),
                    payment.getOrder().getOrderId(),
                    payment.getPaymentId(),payment.getRazorpayOrderId(),
                    payment.getRazorpayPaymentId(),
                    payment.getAmount(),
                    payment.getMethod(),
                    payment.getUpiId(), payment.getStatus(),
                    payment.getTransactionId(),
                    payment.getContact(), payment.getEmail(),
                    payment.getRrn(), payment.getCreatedAt(),
                    payment.getMerchant().getMerchantName(),
                    payment.getMerchant().getMerchantUpiId(),
                    payment.getMerchant().getMerchantBank()
                    );
                    break;
                case "card":
                    dto = new OrderSuccessDTO(
                            //payment.getOrderId(),
                            payment.getOrder().getOrderId(),
                            payment.getPaymentId(),
                            payment.getRazorpayOrderId(),
                            payment.getRazorpayPaymentId(),
                            payment.getAmount(),
                            payment.getMethod(), payment.getStatus(),
                            payment.getContact(), payment.getEmail(),
                            payment.getCreatedAt(), payment.getMerchant().getMerchantName(),
                            payment.getMerchant().getMerchantBank(),
                            payment.getCardDetails(),
                            payment.getCardNetwork(),
                            payment.getCardType(),
                            payment.getIssuer(),
                            payment.getCardSubType(),
                            payment.getAuthCode(),
                            payment.isEmiEligible()!=null? payment.isEmiEligible():false,
                            payment.isInternational()!=null? payment.isInternational():false,
                            payment.getEmiStatus() );
                    System.out.println("Card with OrderSuccessDTO calling");
                    System.out.println("DTO Contact = " + payment.getContact());
                    System.out.println("DTO Email = " + payment.getEmail());
                    System.out.println("DTO PaymentId = " + payment.getPaymentId());
                    System.out.println("DTO RazorpayPaymentId = " + payment.getRazorpayPaymentId());
                    System.out.println("DTO OrderId = " + payment.getOrder().getOrderId());
                    System.out.println("DTO Status = " + payment.getStatus());
                    break;
                case "netbanking":
                    dto = new OrderSuccessDTO(
                           // payment.getOrderId(),
                            payment.getOrder().getOrderId(),
                            payment.getPaymentId(),
                            payment.getRazorpayOrderId(),
                            payment.getRazorpayPaymentId(),
                            payment.getAmount(),
                            payment.getMethod(),
                            payment.getStatus(),
                            payment.getContact(),
                            payment.getEmail(),
                            //payment.getCreatedAt(),
                            payment.getOrder().getCreatedAt(),
                            payment.getMerchant().getMerchantName(),
                            payment.getMerchant().getMerchantBank(),
                            payment.getBank(),   // specific to netbanking
                            payment.getTransactionId()
                    );
                    System.out.println("Netbanking with ordersuccessdto calling");
                    System.out.println("DTO Contact = " + payment.getContact());
                    System.out.println("DTO Email = " + payment.getEmail());
                    System.out.println("DTO Bank = " + payment.getBank());
                    System.out.println("DTO PaymentId = " + payment.getPaymentId());
                    break;


                default: throw new IllegalArgumentException("Unsupported payment method: " + payment.getMethod());
            }
            return  Optional.of(dto);


        }
        return Optional.empty();
    }


}
