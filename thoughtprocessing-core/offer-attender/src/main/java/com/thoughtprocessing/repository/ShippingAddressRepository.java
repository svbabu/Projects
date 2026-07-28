package com.thoughtprocessing.repository;

import com.thoughtprocessing.model.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Long> {
    Optional<ShippingAddressEntity> findById(Long id);

    List<ShippingAddressEntity> findAllByUserId(String userId);

    //@Query("SELECT a FROM ShippingAddressEntity a WHERE a.userId = :userId AND a.useDefault = true")
    //Optional<ShippingAddressEntity> findDefaultAddress(@Param("userId") String userId);

    Optional<ShippingAddressEntity> findByUserId(String userId);

    @Query("SELECT a FROM ShippingAddressEntity a WHERE a.userId = :userId AND a.useDefault = true")
    Optional<ShippingAddressEntity> findDefaultByUserId(@Param("userId") String userId);

   // Optional<ShippingAddressEntity> findById(String id);
/*
    Optional<ShippingAddressEntity> findByUseDefaultTrue();*/
}










