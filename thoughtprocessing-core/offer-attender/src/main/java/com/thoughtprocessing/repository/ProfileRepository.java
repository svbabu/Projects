package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.ProfileEntity;
import com.thoughtprocessing.model.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findById(Long id);

    Optional<ProfileEntity> findByUserId(String userId);

    static List<ProfileEntity> findAllByUserId(String userId) {
        return null;
    }
}