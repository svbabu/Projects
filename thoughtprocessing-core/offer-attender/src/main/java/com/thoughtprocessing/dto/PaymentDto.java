package com.thoughtprocessing.dto;

import java.time.LocalDateTime;

public class PaymentDto {
    private Long id;
    private String bank;
    private Long amount;
    private String status;       // PENDING, SUCCESS, FAILED
    private String orderId;
    private String paymentId;    // Razorpay payment_id
    private String method;       // upi, netbanking, card, wallet
    private String upiId;
    private String transactionId;
    private String personName;
    private String companyName;
    private String contact;
    private String email;
    private LocalDateTime createdAt;
    private String rrn;
    private String cardDetails;
    private String cardNetwork;
    private String cardType;
    private String issuer;
    private String cardSubType;
    private String authCode;
    private Boolean emiEligible;
    private Boolean international;
    private String emiStatus;

    // Constructors
    public PaymentDto() {}
    public PaymentDto(Long id, String bank, Long amount, String status, String orderId,
                      String paymentId, String method, String upiId, String transactionId,
                      String personName, String companyName, String contact, String email,
                      LocalDateTime createdAt, String rrn, String cardDetails, String cardNetwork,
                      String cardType, String issuer, String cardSubType, String authCode,
                      Boolean emiEligible, Boolean international, String emiStatus) {
        this.id = id;
        this.bank = bank;
        this.amount = amount;
        this.status = status;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.method = method;
        this.upiId = upiId;
        this.transactionId = transactionId;
        this.personName = personName;
        this.companyName = companyName;
        this.contact = contact;
        this.email = email;
        this.createdAt = createdAt;
        this.rrn = rrn;
        this.cardDetails = cardDetails;
        this.cardNetwork = cardNetwork;
        this.cardType = cardType;
        this.issuer = issuer;
        this.cardSubType = cardSubType;
        this.authCode = authCode;
        this.emiEligible = emiEligible;
        this.international = international;
        this.emiStatus = emiStatus;
    }

    // getters & setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
        this.cardDetails = cardDetails;
    }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Boolean getEmiEligible() {
        return emiEligible;
    }

    public void setEmiEligible(Boolean emiEligible) {
        this.emiEligible = emiEligible;
    }

    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    public String getEmiStatus() {
        return emiStatus;
    }

    public void setEmiStatus(String emiStatus) {
        this.emiStatus = emiStatus;
    }
    /*public static PaymentDto fromEntity(Payment payment) {
        if (payment == null) return null;

        return new PaymentDto(
                payment.getId(),
                payment.getBank(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getOrder() != null ? payment.getOrder().getOrderId() : null,
                payment.getPaymentId(),
                payment.getMethod(),
                payment.getUpiId(),
                payment.getTransactionId(),
                payment.getPersonName(),
                payment.getCompanyName(),
                payment.getContact(),
                payment.getEmail(),
                payment.getCreatedAt(),
                payment.getRrn(),
                payment.getCardDetails(),
                payment.getCardNetwork(),
                payment.getCardType(),
                payment.getIssuer(),
                payment.getCardSubType(),
                payment.getAuthCode(),
                payment.getEmiEligible(),
                payment.getInternational(),
                payment.getEmiStatus()
        );
    }*/

}

