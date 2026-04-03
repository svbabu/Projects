package com.thoughtprocessing.controller;

import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.thoughtprocessing.dto.NetBankingRequest;
import com.thoughtprocessing.dto.OrderSuccessDTO;
import com.thoughtprocessing.dto.RazorpayResponse;
import com.thoughtprocessing.model.Merchant;
import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.Payment;
import com.thoughtprocessing.repository.MerchantRepository;
import com.thoughtprocessing.repository.OrderRepository;
import com.thoughtprocessing.repository.PaymentRepository;
import com.thoughtprocessing.service.PaymentService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.razorpay.Utils.verifySignature;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    /*private static final String RAZORPAY_SECRET = "eBbfJ27F8g8SHL3bTueHVVG4";*/ // 👈 declare here
    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;  // <-- declare here

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    MerchantRepository merchantRepository;

    @PostMapping("/netbanking")
    public ResponseEntity<Map<String, String>> startNetBankingPayment(@RequestBody NetBankingRequest request) {
        try {
            System.out.println("Received amount: " + request.getAmount());
            Long amountInRupees = request.getAmount();
            if (amountInRupees == null || amountInRupees <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            /*int amountInPaise = (int) (amountInRupees * 100);*/
            int amountInPaise = amountInRupees.intValue(); // ✅ no conversion needed
            System.out.println("Received amount: " + amountInPaise);

            System.out.println("Received amount from after convert: " + amountInPaise);

            // Step 1: Save initial payment record
            Payment saved = paymentService.savePaymentWithOrder(request.getBank(), Long.valueOf(amountInPaise));

            // Step 2: Create Razorpay
            System.out.println("Received amount: " + amountInPaise);
            String orderJson = paymentService.createOrder((int) amountInPaise);
            System.out.println("Received amount: " + orderJson);
            JSONObject orderObj = new JSONObject(orderJson);
            String razorpayOrderId = orderObj.getString("id");

            // Step 3: Update payment record with orderId
            Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));
            merchant.setMerchantName(merchant.getMerchantName());
            merchant.setMerchantBank(merchant.getMerchantBank());
            com.thoughtprocessing.model.Order  order = orderRepository.findByOrderId(razorpayOrderId)
                    .orElseGet(() -> {
                        com.thoughtprocessing.model.Order newOrder = new com.thoughtprocessing.model.Order();
                        newOrder.setOrderId(razorpayOrderId);
                        newOrder.setOrderStatus("PENDING");
                        newOrder.setTotalAmount((long) amountInPaise);
                        newOrder.setAttempts(0); // ✅ initialize attempts
                        return orderRepository.save(newOrder);

                    });
            saved.setMerchant(merchant);
            saved.setOrder(order);

           /* saved.setOrderId(razorpayOrderId);*/
            paymentService.updatePayment(saved);

            // Step 4: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put("paymentId", saved.getId().toString());
            response.put("orderId", razorpayOrderId);
            response.put("amount", String.valueOf(request.getAmount()));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create order: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/upi")
    public ResponseEntity<Map<String, String>> startUpiPayment(@RequestBody NetBankingRequest request) {
        try {
            Long amountInRupees = request.getAmount();
            if (amountInRupees == null || amountInRupees <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            int amountInPaise = amountInRupees.intValue();
            // Step 1: Save initial payment record for UPI
            Payment saved = paymentService.saveUpiPayment(request.getUpiId(), Long.valueOf(amountInPaise));
            // Step 2: Create Razorpay order
            String orderJson = paymentService.createOrder(amountInPaise);
            JSONObject orderObj = new JSONObject(orderJson);
            String razorpayOrderId = orderObj.getString("id");
            // Step 3: Update payment record with orderId
            // Create local Order entity and save it
            Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));
             merchant.setMerchantName(merchant.getMerchantName());
               merchant.setMerchantUpiId(merchant.getMerchantUpiId());
               merchant.setMerchantBank(merchant.getMerchantBank());
            com.thoughtprocessing.model.Order  order = orderRepository.findByOrderId(razorpayOrderId)
                    .orElseGet(() -> {
            com.thoughtprocessing.model.Order newOrder = new com.thoughtprocessing.model.Order();
                        newOrder.setOrderId(razorpayOrderId);
                        newOrder.setOrderStatus("PENDING");
                        newOrder.setTotalAmount((long) amountInPaise);
                        newOrder.setAttempts(0); // ✅ initialize attempts
            return orderRepository.save(newOrder);

                    });
            // ✅ Increment attempts for retry
            order.setAttempts(order.getAttempts() + 1);
            orderRepository.save(order);


           /* saved.setOrderId(orderId);*/
            saved.setMerchant(merchant);
            saved.setOrder(order);    // attached Order relation to Payment
            paymentService.updatePayment(saved);
            // Step 4: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put("paymentId", saved.getId().toString());
            response.put("orderId", razorpayOrderId);
            response.put("amount", String.valueOf(request.getAmount()));
            logger.info("Order {} and Payment {} saved successfully", order.getOrderId(), saved.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create UPI order: " + e.getMessage());

            return ResponseEntity.status(500).body(error);
        }
    }

    @PostMapping("/card")
    public ResponseEntity<Map<String, String>> startCardPayment(@RequestBody NetBankingRequest request) {
        try {
            Long amountInRupees = request.getAmount();
            if (amountInRupees == null || amountInRupees <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            int amountInPaise = amountInRupees.intValue();

            // Step 1: Save initial payment record for Card
            Payment saved = paymentService.saveCardPayment(Long.valueOf(amountInPaise));
            // Step 2: Create Razorpay order
            String orderJson = paymentService.createOrder(amountInPaise);
            JSONObject orderObj = new JSONObject(orderJson);
            String razorpayOrderId = orderObj.getString("id");
            // Step 3: Update payment record with orderId
            Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));

            merchant.setMerchantName(merchant.getMerchantName());
            merchant.setMerchantUpiId(merchant.getMerchantUpiId());
            merchant.setMerchantBank(merchant.getMerchantBank());


            com.thoughtprocessing.model.Order  order = orderRepository.findByOrderId(razorpayOrderId)
                    .orElseGet(() -> {
                                com.thoughtprocessing.model.Order newOrder = new com.thoughtprocessing.model.Order();
                        newOrder.setOrderId(razorpayOrderId);
                        newOrder.setOrderStatus("PENDING");
                        newOrder.setTotalAmount((long) amountInPaise);
                        newOrder.setAttempts(0); // ✅ initialize attempts
                        return orderRepository.save(newOrder);

                    });
           /* saved.setOrderId(razorpayOrderId);*/
            saved.setMerchant(merchant);
            saved.setOrder(order);
            paymentService.updatePayment(saved);
            // Step 2: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put("dbpaymentId", saved.getId().toString());
            response.put("orderId", razorpayOrderId);
            response.put("amount", String.valueOf(request.getAmount()));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create Card order: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<Map> verifyPayment(@RequestBody RazorpayResponse response) {
         if (response.getOrderId() == null) {
             Payment payment = paymentRepository.findByPaymentId(response.getPaymentId());
             if (payment != null) {
                 response.setOrderId(payment.getOrderId());
                 System.out.println("orderid"+payment.getOrderId());
             }
         }

         if (response == null || response.getOrderId() == null || response.getPaymentId() == null)
         {
             System.out.println("Invalid payload: orderId=" + response.getOrderId()
                     + ", paymentId=" + response.getPaymentId()
                     + ", signature=" + response.getSignature());
           /*  System.out.println("Invalid payload: " + response);*/
             return ResponseEntity.badRequest().body(Map.of( "status", "failed", "message", "Missing orderId or paymentId" ));
         }
         boolean isValid = paymentService.verifySignature(response);
         Map<String, String> result = new HashMap<>();
         if (!isValid) {
             result.put("status", "failed"); result.put("message", "Invalid signature");
             return ResponseEntity.badRequest().body(result); }
         // Delegate to finalizePayment so DB gets updated
         try { Payment updated = paymentService.finalizePayment(response);
             result.put("status", updated.getStatus());
             result.put("orderId", updated.getOrderId());
             result.put("paymentId", updated.getPaymentId());
             return ResponseEntity.ok(result);
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(Map.of("status","failed","message",e.getMessage()));
         }

     }
    @GetMapping("/webhook")
    public ResponseEntity<String> webhookHealth() {

        return ResponseEntity.ok("Webhook endpoint alive");
    }
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("X-Razorpay-Signature") String signature) throws RazorpayException {
        System.out.println("Webhook raw payload: " + payload);
        System.out.println("Webhook signature: " + signature);
        boolean isValid = Utils.verifyWebhookSignature(payload, signature, webhookSecret);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }
        // Process payload if valid
        System.out.println("Verified payload: " + payload);
        JSONObject json = new JSONObject(payload);
        String razorpayOrderId = json.getJSONObject("payload")
                                     .getJSONObject("payment")
                                     .getJSONObject("entity")
                                     .getString("order_id");

        // Update your DB
        Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findByOrderId(razorpayOrderId));

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            JSONObject paymentEntity = json.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");
            String razorpayPaymentId = paymentEntity.getString("id");
            payment.setPaymentId(razorpayPaymentId);


            // Fetch payment method (already present in payload)
            // Core fields
            com.thoughtprocessing.model.Order order = orderRepository.findByOrderId(razorpayOrderId) .
                    orElseThrow(() -> new RuntimeException("Order not found"));
            String orderId = order.getOrderId();
            payment.setOrder(order);



            payment.setMethod(paymentEntity.optString("method", null));
            payment.setAmount(paymentEntity.optLong("amount", 0L));
            payment.setContact(paymentEntity.optString("contact", null));
            payment.setEmail(paymentEntity.optString("email", null));
            payment.setBank(paymentEntity.optString("bank", null));
            payment.setUpiId(paymentEntity.optString("vpa", null));
            // ✅ Nested acquirer_data
            if (paymentEntity.has("acquirer_data")) {
                JSONObject acquirerData = paymentEntity.getJSONObject("acquirer_data");
                payment.setRrn(acquirerData.optString("rrn", null));
                payment.setTransactionId(acquirerData.optString("upi_transaction_id", null));
                payment.setTransactionId(acquirerData.optString("bank_transaction_id", null));


            }

            // ✅ Nested card details
            if (paymentEntity.has("card")) { JSONObject card = paymentEntity.getJSONObject("card");
                payment.setCardDetails("XXXX-XXXX-XXXX-" + card.optString("last4", ""));
                payment.setCardNetwork(card.optString("network", null));
                payment.setCardType(card.optString("type", null));
                payment.setIssuer(card.optString("issuer", null));
                payment.setCardSubType(card.optString("sub_type", null));
                payment.setEmiEligible(card.optBoolean("emi", false));
                payment.setInternational(card.optBoolean("international", false)); }
            // ✅ Timestamp conversion
            long createdAtEpoch = paymentEntity.optLong("created_at", 0L);
            if (createdAtEpoch > 0) {
                LocalDateTime createdAt = Instant.ofEpochSecond(createdAtEpoch)
                        .atZone(ZoneId.systemDefault()) .toLocalDateTime();
                payment.setTimestamp(createdAt);
            }

            // Update your DB entity

           /* if(upiId != null)
            payment.setUpiId(upiId);       // <-- new column in your Payment table
            payment.setMethod(method); */    // <-- if you want to persist method too
            String event = json.getString("event");
            switch (event) {
                case "payment.captured":
                    payment.setStatus("COMPLETED");
                    break;
                    case "payment.failed":
                        payment.setStatus("FAILED");
                        break;
                        case "payment.authorized":
                            payment.setStatus("AUTHORIZED");
                            break;
            }


            paymentRepository.save(payment);
        }
        return ResponseEntity.ok("Webhook received");

        }
    @PostMapping("/finalize")
    public ResponseEntity<Payment> finalizePayment(@RequestBody RazorpayResponse response) {
        try {
            Payment updatedPayment = paymentService.finalizePayment(response);
            return ResponseEntity.ok(updatedPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //cod
    @PostMapping("/cod")
    public ResponseEntity<Map<String, String>> startCodPayment(@RequestBody NetBankingRequest request) {
        try {
            Long amountInRupees = request.getAmount();
            if (amountInRupees == null || amountInRupees <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            // Step 1: Save initial payment record for COD

            Payment saved = paymentService.saveCodPayment(amountInRupees);
// Step 2: Mark status as PENDING (until delivery is completed)
            saved.setStatus("PENDING");
            saved.setMethod("COD");
            paymentService.updatePayment(saved);
// Step 3: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put("dbpaymentId", saved.getId().toString());
            response.put("amount", String.valueOf(amountInRupees));
            response.put("status", saved.getStatus());
            response.put("method", saved.getMethod());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create COD order: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    //ordersuccess

    @GetMapping("/ordersuccess/{orderId}")
    public ResponseEntity<OrderSuccessDTO> getOrderDtoResponse(@PathVariable String orderId)
    {

                   try {
                       return paymentService.getOrderSuccDetls(orderId)
                               .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
                       }
                   catch (Exception e) {
                       logger.error("Failed to fetch Order Success details for orderId={}", orderId, e);
                       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
    }



}
