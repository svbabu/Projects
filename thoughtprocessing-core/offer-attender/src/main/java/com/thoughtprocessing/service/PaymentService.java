package com.thoughtprocessing.service;

import com.razorpay.Order;

import com.thoughtprocessing.dto.OrderSuccessDTO;
import com.thoughtprocessing.dto.RazorpayResponse;
import com.thoughtprocessing.model.Merchant;
import com.thoughtprocessing.model.Payment;
import com.thoughtprocessing.repository.MerchantRepository;
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



import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MerchantRepository merchantRepository;
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
    public Payment savePaymentWithOrder(String bank, Long amount) throws Exception {
        System.out.println("Received amount: " + amount);
        Payment payment = new Payment();
        payment.setBank(bank);  //Net banking call use bank url for login with pay
        payment.setAmount(amount);
        payment.setStatus("PENDING");

        logger.info("Payment Added Successfully");
        logger.info("Payment Added Successfully: Bank={} Amount={}", payment.getBank(), payment.getAmount());

        // Create Razorpay order
        String orderJson = createOrder(Math.toIntExact(amount));
        JSONObject orderObj = new JSONObject(orderJson);
        String razorpayOrderId = orderObj.getString("id");
        // Create local Order entity and save it
        Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        com.thoughtprocessing.model.Order order = new com.thoughtprocessing.model.Order();
        order.setOrderId(razorpayOrderId);
        order.setOrderStatus("PENDING");
        order.setTotalAmount(amount);
        order.setAttempts(0); // ✅ initialize attempts
        orderRepository.save(order);

        /*payment.setOrderId(orderId);*/
        payment.setMerchant(merchant);
        payment.setOrder(order);   // ✅ attach relationship
        logger.info("Order {} and Payment {} saved successfully", order.getOrderId(), payment.getPaymentId());
        return paymentRepository.save(payment);
    }
    public Payment saveUpiPayment(String upiId, Long amount) throws Exception {
        System.out.println("Received UPI payment request: " + upiId + " Amount: " + amount);
        // Create Razorpay order
        String orderJson = createOrder(Math.toIntExact(amount));
        JSONObject orderObj = new JSONObject(orderJson);
        String razorpayOrderId = orderObj.getString("id");

        // Create local Order entity and save it
     /*  com.thoughtprocessing.model.Order order = new com.thoughtprocessing.model.Order();
        order.setOrderId(razorpayOrderId);
        order.setOrderStatus("PENDING");
        order.setTotalAmount(amount);
        order.setAttempts(0); // ✅ initialize attempts
        orderRepository.save(order);*/
        Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                .orElseThrow(() -> new RuntimeException("Merchant not found"));


       com.thoughtprocessing.model.Order  order = orderRepository.findByOrderId(razorpayOrderId)
                .orElseGet(() -> {
                    com.thoughtprocessing.model.Order newOrder = new com.thoughtprocessing.model.Order();
                    newOrder.setOrderId(razorpayOrderId);
                    newOrder.setOrderStatus("PENDING");
                    newOrder.setTotalAmount(amount);
                   newOrder.setAttempts(0); //initialize
                    return orderRepository.save(newOrder);

                });
        // ✅ Increment attempts for retry
        order.setAttempts(order.getAttempts() + 1);
        orderRepository.save(order);



        Payment payment = new Payment();
        payment.setUpiId(upiId);
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        payment.setMethod("upi");

        payment.setMerchant(merchant);
        payment.setOrder(order); // ✅ attach relationship
        /*payment.setOrder(order1);*/
        logger.info("UPI Payment Added Successfully: UPI={} Amount={}", payment.getUpiId(), payment.getAmount());

       /* // Create Razorpay order
        String orderJson = createOrder(Math.toIntExact(amount));
        JSONObject orderObj = new JSONObject(orderJson);
        String orderId = orderObj.getString("id");

        payment.setOrderId(orderId);*/

        logger.info("Order {} and Payment {} saved successfully", order.getOrderId(), payment.getPaymentId());
        return paymentRepository.save(payment);
    }
    public Payment saveCardPayment(Long amount) throws Exception {
        System.out.println("Received Card payment request: Amount=" + amount);

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        payment.setMethod("card");

        logger.info("Card Payment Added Successfully: Amount={}", payment.getAmount());

        // Create Razorpay order
        String orderJson = createOrder(Math.toIntExact(amount));
        JSONObject orderObj = new JSONObject(orderJson);
        String razorpayOrderId = orderObj.getString("id");


        Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        com.thoughtprocessing.model.Order  order = orderRepository.findByOrderId(razorpayOrderId)
                .orElseGet(() -> {
                    com.thoughtprocessing.model.Order newOrder = new com.thoughtprocessing.model.Order();
                    newOrder.setOrderId(razorpayOrderId);
                    newOrder.setOrderStatus("PENDING");
                    newOrder.setTotalAmount(amount);
                    newOrder.setAttempts(0);
                    return orderRepository.save(newOrder); });

        payment.setMerchant(merchant);
        payment.setOrderId(razorpayOrderId);
        logger.info("Order {} and Payment {} saved successfully", order.getOrderId(), payment.getPaymentId());
        return paymentRepository.save(payment);
    }
    public String createOrder(int amount) throws Exception {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount ); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        // Correct way: use razorpayClient.orders.create(...)
        Order order = razorpayClient.orders.create(orderRequest);

        return order.toString(); // or return order.get("id")
    }
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public boolean verifySignature(RazorpayResponse response) {
        try {
            System.out.println("Verify request: orderId=" + response.getOrderId() +
                    ", paymentId=" + response.getPaymentId() +
                    ", signature=" + response.getSignature());


            String payload = response.getOrderId() + "|" + response.getPaymentId();
           /* System.out.println("Payload: " + payload);*/
            logger.debug("Payload: {}", payload);


            String actualSignature = response.getSignature();

           /* String secret = "eBbfJ27F8g8SHL3bTueHVVG4";*/ // Use env variable in production
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
            e.printStackTrace();
            return "failed";
        }
    }

    public Payment finalizePayment(RazorpayResponse response) throws Exception {
        if (verifySignature(response)) {
            System.out.println("finalizePayment{} method is called");
            // Fetch full payment details from Razorpay
            com.razorpay.Payment razorpayPayment = razorpayClient.payments.fetch(response.getPaymentId());
            System.out.println();
            JSONObject upiObj = razorpayPayment.get("upi"); // returns a JSONObject
            String upiId = null;
            if (upiObj != null&& upiObj.has("payer_account")) {
                System.out.println(upiObj.get("upiId"));
                upiId = upiObj.getString("payer_account");
            }

            String status = razorpayPayment.get("status"); // authorized, captured, failed
            System.out.println("Payment Status: " + status);
            // Update local DB record
            Payment payment = paymentRepository.findByOrderId(response.getOrderId());
            if(payment==null){
                throw new IllegalArgumentException("No payment found for orderId " + response.getOrderId());
            }
            System.out.println("After save: " + payment.getPaymentId());


            payment.setPaymentId(response.getPaymentId());
            System.out.println("Payment Id: " + payment.getPaymentId());
            if(upiId!=null)
            payment.setUpiId(upiId);
            System.out.println("Payment UpiId: " + payment.getUpiId());
            /*payment.setStatus(razorpayPayment.get("status"));*/ // authorized, captured, failed
             payment.setStatus(status);

            System.out.println("Payment Status: " + payment.getStatus());
            System.out.println("Before save: " + payment.getPaymentId());


            // 🔑 Update order as well
            com.thoughtprocessing.model.Order order = orderRepository.
                    findById(response.getOrderId()) .orElseThrow(() ->
                            new RuntimeException("Order not found"));
            order.setOrderStatus(status.equals("captured") ? "PAID" : "PAYMENT_FAILED");
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);


            return paymentRepository.save(payment);
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
      /*  JSONObject paymentEntity = json.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity");
        JSONObject acquirerData = paymentEntity.getJSONObject("acquirer_data");*/
        Payment payment = paymentRepository.findByOrderId(OrderId);
        if (payment != null) {
            OrderSuccessDTO dto;
            switch (payment.getMethod()) {
                case "upi":
            dto=new OrderSuccessDTO(
                    payment.getOrderId(),
                    payment.getPaymentId(),
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
                    dto = new OrderSuccessDTO( payment.getOrderId(),
                            payment.getPaymentId(), payment.getAmount(),
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
                    break;
                case "netbanking":
                    dto = new OrderSuccessDTO(
                            payment.getOrderId(),
                            payment.getPaymentId(),
                            payment.getAmount(),
                            payment.getMethod(),
                            payment.getStatus(),
                            payment.getContact(),
                            payment.getEmail(),
                            payment.getCreatedAt(),
                            payment.getMerchant().getMerchantName(),
                            payment.getMerchant().getMerchantBank(),
                            payment.getBank(),   // specific to netbanking
                            payment.getTransactionId()
                    );
                    break;


                default: throw new IllegalArgumentException("Unsupported payment method: " + payment.getMethod());
            }
            return  Optional.of(dto);


        }
        return Optional.empty();
    }
}
