package com.thoughtprocessing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RazorpayResponse {
    @JsonProperty("razorpay_payment_id")
    String paymentId;
    @JsonProperty("razorpay_order_id")
    private String orderId;
    @JsonProperty("razorpay_signature")
    private String signature;
    // Getters and setters
    public RazorpayResponse() {

    }
    public RazorpayResponse( String orderId,String paymentId, String signature) {

        this.orderId = orderId;
        this.paymentId = paymentId;
        this.signature = signature;
    }
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override public String toString() {
        return "RazorpayResponse{" + "orderId='" + orderId + '\'' + ", paymentId='" + paymentId + '\'' + ", signature='" + signature + '\'' + '}';
    }





}
