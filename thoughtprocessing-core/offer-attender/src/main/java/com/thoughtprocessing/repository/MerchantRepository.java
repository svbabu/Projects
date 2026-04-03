package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant,Long> {

    Optional<Merchant> findById(Long merchantId);
    Optional<Merchant> findByMerchantName(String merchantName);
    Optional<Merchant> findByMerchantUpiId(String merchantUpiId);
    Optional<Merchant> findByMerchantBank(String merchantBank);
}
