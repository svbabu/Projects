package com.thoughtprocessing.dto;

import java.time.LocalDateTime;

public class OrderSuccessDTO {

    private Long id; // internal reference private
    private String bank; // bank name if applicabl
     private Long amount; // transaction amount
     private String status; // PENDING, SUCCESS, FAILED
     private String orderId; // your system order reference
     private String paymentId; // Razorpay payment_id
     private String method; // upi, netbanking, card, wallet
     private String upiId; // optional, only for UPI
    // Enriched fields
    private String transactionId; // internal/external transaction reference
    private String personName; // payer’s name
    private String companyName; // paid company/merchant name
    private String contact; // phone number
    private String email; // payer’s email
    private String rrn;
    private LocalDateTime createdAt;
    private String merchantId;
    private String merchantName;
    private String merchantUpiId;
    private String merchantBank;

    private String emiStatus;
    private  boolean emiEligible;
    private  boolean international;
    private  String authCode;
    private  String cardSubType;
    private  String issuer;
    private  String cardType;
    private  String cardNetwork;
    private  String cardDetails;

    public OrderSuccessDTO()
    {}
    public OrderSuccessDTO(Long id, String bank, Long amount, String status,
                           String orderId, String paymentId, String method,
                           String upiId,String transactionId, String personName,
                           String companyName, String contact, String email,LocalDateTime createdAt,String rrn)
    {
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


    }


    public OrderSuccessDTO(String orderId, String paymentId, Long amount, String method, String upiId, String status, String transactionId, String contact, String email, String rrn, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.upiId = upiId;
        this.status = status;
        this.transactionId = transactionId;
        this.contact=contact;
        this.email=email;
        this.rrn=rrn;
        this.createdAt =createdAt;


    }
     //upi constrcucor with new
    public OrderSuccessDTO(String orderId, String paymentId, Long amount, String method, String upiId, String status, String transactionId, String contact, String email, String rrn, LocalDateTime createdAt, String merchantName, String merchantUpiId, String merchantBank) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.upiId = upiId;
        this.status = status;
        this.transactionId = transactionId;
        this.contact=contact;
        this.email=email;
        this.rrn=rrn;
        this.createdAt =createdAt;
        this.merchantName = merchantName;
        this.merchantUpiId=merchantUpiId;
        this.merchantBank=merchantBank;

    }

    //write card construcotor
    public OrderSuccessDTO(String orderId, String paymentId, Long amount, String method,
                           String status, String contact, String email, LocalDateTime createdAt,
                           String cardDetails, String cardNetwork, String cardType, String issuer,
                           String cardSubType, String authCode, boolean emiEligible,
                           boolean international, String emiStatus) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.contact = contact;
        this.email = email;
        this.createdAt = createdAt;
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
    //card  constructor impl
    public OrderSuccessDTO(String orderId, String paymentId, Long amount, String method,
                           String status, String contact, String email, LocalDateTime createdAt,
                           String merchantName,  String merchantBank,
                           String cardDetails, String cardNetwork, String cardType, String issuer,
                           String cardSubType, String authCode, boolean b, boolean b1, String emiStatus) {
    this.orderId = orderId;
    this.paymentId = paymentId;
    this.amount = amount;
    this.method = method;
    this.status = status;
    this.contact = contact;
    this.email = email;
    this.createdAt = createdAt;
    this.cardDetails = cardDetails;
    this.cardNetwork = cardNetwork;
    this.cardType = cardType;
    this.issuer = issuer;
    this.cardSubType = cardSubType;
    this.authCode = authCode;
    this.emiEligible = b;
    this.emiStatus = emiStatus;
    this.merchantName = merchantName;
    this.merchantBank = merchantBank;
    this.international = b1;
    }
//netbanking constructor
    public OrderSuccessDTO(String orderId, String paymentId, Long amount, String method, String status, String contact, String email, LocalDateTime createdAt, String merchantName, String merchantBank, String bank, String transactionId) {
    this.orderId = orderId;
    this.paymentId = paymentId;
    this.amount = amount;
    this.method = method;
    this.status = status;
    this.contact = contact;
    this.email = email;
    this.createdAt = createdAt;
    this.merchantName = merchantName;
    this.merchantBank = merchantBank;
    this.bank = bank;
    this.transactionId = transactionId;


    }


    public String getEmiStatus() {
        return emiStatus;
    }

    public void setEmiStatus(String emiStatus) {
        this.emiStatus = emiStatus;
    }

    public boolean isEmiEligible() {
        return emiEligible;
    }

    public void setEmiEligible(boolean emiEligible) {
        this.emiEligible = emiEligible;
    }

    public boolean isInternational() {
        return international;
    }

    public void setInternational(boolean international) {
        this.international = international;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCardSubType() {
        return cardSubType;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
        this.cardDetails = cardDetails;
    }



    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantUpiId() {
        return merchantUpiId;
    }

    public void setMerchantUpiId(String merchantUpiId) {
        this.merchantUpiId = merchantUpiId;
    }

    public String getMerchantBank() {
        return merchantBank;
    }

    public void setMerchantBank(String merchantBank) {
        this.merchantBank = merchantBank;
    }

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
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
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

}
