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
@CrossOrigin("origins=http://localhost:8080")
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
            System.out.println(
                    "NETBANKING API CALLED: " + LocalDateTime.now()
            );
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
            System.out.println(
                    "request.getOrderId() = " + request.getOrderId()
            );
            Payment saved = paymentService.savePaymentWithOrder(
                    request.getOrderId(), // checkout order id
                    request.getBank(),
                    Long.valueOf(amountInPaise));

            // Step 2: Create Razorpay
            System.out.println("Received amount: " + amountInPaise);

            System.out.println(
                    "Creating Razorpay order for amount "
                            + amountInPaise
            );

            // Step 3: Update payment record with orderId
            Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));
            merchant.setMerchantName(merchant.getMerchantName());
            merchant.setMerchantBank(merchant.getMerchantBank());

            System.out.println(
                    "PaymentId=" + saved.getId()
                            + ", RazorpayOrderId=" + saved.getRazorpayOrderId()
            );

            // Step 4: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put(
                    "paymentId",
                    saved.getId().toString()
            );

            response.put(
                    "orderId",
                    saved.getRazorpayOrderId()
            );

            response.put(
                    "amount",
                    String.valueOf(request.getAmount())
            );

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
            System.out.println(
                    "UPI API CALLED: " + LocalDateTime.now()
            );
            System.out.println("Received amount: " + request.getAmount());
            Long amountInRupees = request.getAmount();
            if (amountInRupees == null || amountInRupees <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            int amountInPaise = amountInRupees.intValue();
            System.out.println("Received amount: " + amountInPaise);

            System.out.println("Received amount from after convert: " + amountInPaise);

            // Step 1: Save initial payment record
            System.out.println(
                    "request.getOrderId() = " + request.getOrderId()
            );

            // Step 1: Save initial payment record for UPI
            Payment saved = paymentService.saveUpiPayment( request.getOrderId(), // checkout order id
                     request.getUpiId(),
                    Long.valueOf(amountInPaise));

            // Step 2: Create Razorpay
            System.out.println("Received amount: " + amountInPaise);

            System.out.println(
                    "Creating Razorpay order for amount "
                            + amountInPaise
            );
            // Step 2: Create Razorpay order
           // String orderJson = paymentService.createOrder(amountInPaise);
            //JSONObject orderObj = new JSONObject(orderJson);
           // String razorpayOrderId = orderObj.getString("id");
            // Step 3: Update payment record with orderId
            // Create local Order entity and save it
            //Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                   // .orElseThrow(() -> new RuntimeException("Merchant not found"));
             //merchant.setMerchantName(merchant.getMerchantName());
             //  merchant.setMerchantUpiId(merchant.getMerchantUpiId());
               //merchant.setMerchantBank(merchant.getMerchantBank());
            // Step 3: Update payment record with orderId
            Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));
            merchant.setMerchantName(merchant.getMerchantName());
            merchant.setMerchantUpiId(merchant.getMerchantUpiId());
            merchant.setMerchantBank(merchant.getMerchantBank());

            System.out.println(
                    "PaymentId=" + saved.getId()
                            + ", RazorpayOrderId=" + saved.getRazorpayOrderId()
            );
            // Step 4: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put(
                    "paymentId",
                    saved.getId().toString()
            );

            response.put(
                    "orderId",
                    saved.getRazorpayOrderId()
            );

            response.put(
                    "amount",
                    String.valueOf(request.getAmount())
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create order: " + e.getMessage());
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
            System.out.println("Received amount: " + amountInPaise);

            System.out.println("Received amount from after convert: " + amountInPaise);

            // Step 1: Save initial payment record
            System.out.println(
                    "request.getOrderId() = " + request.getOrderId()
            );


            // Step 1: Save initial payment record for Card
            Payment saved = paymentService.saveCardPayment(
                    request.getOrderId(), // checkout order id
                    Long.valueOf(amountInPaise));
            // Step 2: Create Razorpay
            System.out.println("Received amount: " + amountInPaise);

            System.out.println(
                    "Creating Razorpay order for amount "
                            + amountInPaise
            );
            // Step 3: Update payment record with orderId
            Merchant merchant = merchantRepository.findById(Long.valueOf(Integer.valueOf(101)))
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));

            merchant.setMerchantName(merchant.getMerchantName());
            merchant.setMerchantUpiId(merchant.getMerchantUpiId());
            merchant.setMerchantBank(merchant.getMerchantBank());
            System.out.println(
                    "PaymentId=" + saved.getId()
                            + ", RazorpayOrderId=" + saved.getRazorpayOrderId()
            );

            // Step 4: Build response for frontend
            Map<String, String> response = new HashMap<>();
            response.put(
                    "paymentId",
                    saved.getId().toString()
            );

            response.put(
                    "orderId",
                    saved.getRazorpayOrderId()
            );

            response.put(
                    "amount",
                    String.valueOf(request.getAmount())
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create order: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<Map> verifyPayment(@RequestBody RazorpayResponse response) {
        if (response == null || response.getRazorpayOrderId() == null || response.getRazorpayPaymentId() == null)
         {
             System.out.println("Invalid payload: orderId=" + response.getRazorpayOrderId()
                     + ", paymentId=" + response.getRazorpayPaymentId()
                     + ", signature=" + response.getSignature());
           /*  System.out.println("Invalid payload: " + response);*/
             return ResponseEntity.badRequest().body(Map.of( "status", "failed", "message", "Missing orderId or paymentId" ));
         }
        // ✅ Always look up by razorpay_order_id
        Payment payment = paymentRepository
                .findByRazorpayOrderId(response.getRazorpayOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Payment not found for orderId=" + response.getRazorpayOrderId()));

        // ✅ Update razorpay_payment_id here
        payment.setRazorpayPaymentId(response.getRazorpayPaymentId());
        payment.setStatus("COMPLETED");
        paymentRepository.save(payment);
        /*if (response.getOrderId() == null) {
            //Payment payment = paymentRepository.findByPaymentId(response.getPaymentId());
            Payment payment = paymentRepository.
                    findByRazorpayPaymentId(
                            response.getRazorpayPaymentId())
                    .orElseThrow(()-> new IllegalArgumentException("Payment not found"));
            response.setOrderId(payment.getOrderId());
            System.out.println("orderid"+payment.getOrderId());

             *//*if (payment != null) {
                 response.setOrderId(payment.getOrderId());
                 System.out.println("orderid"+payment.getOrderId());
             }*//*
            // ✅ Update razorpay_payment_id here
            payment.setRazorpayPaymentId(response.getRazorpayPaymentId());
        }*/
        System.out.println("Before verifySignature");
         boolean isValid = paymentService.verifySignature(response);
        System.out.println("After verifySignature");
        System.out.println("isValid = " + isValid);
        System.out.println(
                "Signature Valid = "
                        + isValid
        );
         Map<String, String> result = new HashMap<>();

         if (!isValid) {
             System.out.println(
                     "RETURNING INVALID SIGNATURE"
             );
             result.put("status", "failed");
             result.put("message", "Invalid signature");
             return ResponseEntity.badRequest().body(result); }
         // Delegate to finalizePayment so DB gets updated
         try {
             System.out.println("Before finalizePayment");
             System.out.println(
                     "ABOUT TO CALL FINALIZE PAYMENT"
             );
             Payment updated = paymentService.finalizePayment(response);
             System.out.println("After finalizePayment");
             result.put("status", updated.getStatus());
             //result.put("orderId", updated.getOrderId());
             result.put("orderId",updated.getOrder().getOrderId());
             result.put("paymentId", updated.getPaymentId());
             result.put("razorpayOrderId", updated.getRazorpayOrderId());
             result.put("razorpayPaymentId", updated.getRazorpayPaymentId());
             System.out.println("Verify Response = " + result);
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
        //Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findByOrderId(razorpayOrderId));
        Optional<Payment> optionalPayment = Optional.ofNullable(paymentRepository.findByOrder_OrderId(razorpayOrderId));

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            JSONObject paymentEntity = json.getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");
            String razorpayPaymentId = paymentEntity.getString("id");
            payment.setPaymentId(razorpayPaymentId);//oldone only set paymentid from bakckend right
            payment.setRazorpayPaymentId(razorpayPaymentId);//THis line missing call right


            // Fetch payment method (already present in payload)
            // Core fields
           /* com.thoughtprocessing.model.Order order = orderRepository.findByOrderId(razorpayOrderId) .
                    orElseThrow(() -> new RuntimeException("Order not found"));
            String orderId = order.getOrderId();*/

            // Look up by Razorpay external order ID
            Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

           /* Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));*/
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
