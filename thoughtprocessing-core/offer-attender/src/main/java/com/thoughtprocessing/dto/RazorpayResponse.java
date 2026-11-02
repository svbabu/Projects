package com.thoughtprocessing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RazorpayResponse {
    //@JsonProperty("razorpay_payment_id")
   // String paymentId;
    @JsonProperty("razorpay_order_id")
    private String razorpayOrderId;   // external Razorpay orderId

    @JsonProperty("razorpay_payment_id")
    private String razorpayPaymentId;
    // Internal IDs (from your system, not Razorpay)
   /* @JsonProperty("order_id")
    private String orderId;*/
    @JsonProperty("razorpay_signature")
    private String signature;
    // Add your own fields
    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("shipping_address_id")
    private Long shippingAddressId;
    // Getters and setters
    public RazorpayResponse() {

    }
    public RazorpayResponse( String orderId,String paymentId, String signature,String customerId,Long shippingAddressId,String razorpayOrderId,String razorpayPaymentId) {

        //this.orderId = orderId;
        //this.paymentId = paymentId;
        this.signature = signature;
        this.customerId = customerId;
        this.shippingAddressId = shippingAddressId;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId=razorpayPaymentId;
    }
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /*public String getPaymentId() {
        return paymentId;
    }*/

   /* public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }*/

   /* public String getOrderId() {
return orderId;
    }*/

    //public void setOrderId(String orderId) {
      //  this.orderId = orderId;
   // }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }
    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }
    @Override public String toString() {
        return "RazorpayResponse{" +
              //  "orderId='" + orderId + '\'' + ", " +
               // "paymentId='" + paymentId + '\'' + ", " +
                "signature='" + signature + '\'' +","+
                "razorpayOrderId='" + razorpayOrderId + '\'' +","+
                "customerId='" + customerId + '\'' +"," +
                "shippingAddressId='" + shippingAddressId + '\'' +'}';

    }



}
