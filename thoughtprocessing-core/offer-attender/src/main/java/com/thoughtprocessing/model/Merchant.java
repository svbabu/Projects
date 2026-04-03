package com.thoughtprocessing.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long merchantId;
    @Column(nullable = false, unique = true)
    private String merchantName;
    @Column(nullable = false)
    private String merchantUpiId;
    @Column(nullable = false)
    private String merchantBank;
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;
    public Merchant() {}
    public Merchant(Long merchantId,String merchantName, String merchantUpiId, String merchantBank) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.merchantUpiId = merchantUpiId;
        this.merchantBank = merchantBank;
        this.payments = new ArrayList<>();
    }
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}
