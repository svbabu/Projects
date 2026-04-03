package com.thoughtprocessing.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // optional if same name
    private Long id;

    @Column(name = "bank")
    private String bank;
    @Column(name = "amount")
    private Long amount;
    @Column(name = "status")
    private String status; // e.g. PENDING, SUCCESS, FAILED
    @Column(name="order_id" ,insertable = false, updatable = false)
    private String orderId;
    @Column(name="paymentId")
    private String paymentId;     // Razorpay payment_id
    @Column(name="method")
    private String method;        // upi, netbanking, card, wallet
    @Column(name="upi_id")
    private String upiId;// optional for UPI
    @Column(name="transaction_id")
    private String transactionId; // internal/external transaction reference
    @Column(name="personName")
    private String personName; // payer’s name
    @Column(name="companyName")
    private String companyName; // paid company/merchant name
    @Column(name="contact")
    private String contact; // phone number
    @Column(name="email")
    private String email; // payer’s email
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="rrn")
    private String rrn;
    @Column(name="card_details")
    private String cardDetails;
    @Column(name ="card_network" )
    private String cardNetwork;
    @Column(name="cardtype")
    private String cardType;
    @Column(name="issuer")
    private String issuer;
    @Column(name="cardsubtype")
    private String cardSubType;
    @Column(name="authcode")
    private String authCode;
    @Column(name="emieligible")
    private Boolean emiEligible;
    @Column(name="international")
    private  Boolean international;
    @Column(name="emistatus")
    private String emiStatus;

    @ManyToOne @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @ManyToOne @JoinColumn(name = "order_id")
    private Order order;               // foreign key to orders table




    public Payment() {
    }
    public Payment(Long id, String bank, Long amount, String status,
                   String orderId, String method, String paymentId, String upiId, String transactionId, String personName,
                   String companyName, String contact, String email, LocalDateTime createdAt,
                   String rrn, String cardDetails, String cardNetwork, String cardType, String issuer,
                   String cardSubType, String authCode, Boolean emiEligible, Boolean  international, String emiStatus,
                   Merchant merchant,
                   Order order ) {
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
        this.emiEligible=emiEligible;
        this.merchant = merchant;
        this.order = order;


        /* this.orderId = orderId;*/
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
        this.cardDetails = cardDetails;
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

    public Boolean isEmiEligible() {
        return emiEligible;
    }

    public void setEmiEligible(Boolean emiEligible) {
        this.emiEligible = emiEligible;
    }

    public Boolean isInternational() {
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
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
    public void setTimestamp(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getRrn() {
        return rrn;
    }
    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
        public Merchant  getMerchant()
        {
            return merchant;
        }

    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }


}

