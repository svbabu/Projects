package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.ShippingUserProfileMapper;
import com.thoughtprocessing.model.ProfileEntity;
import com.thoughtprocessing.model.ShippingAddressEntity;
import com.thoughtprocessing.repository.ProfileRepository;
import com.thoughtprocessing.repository.ShippingAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thoughtprocessing.dto.ShippingAddressDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;




@Service
public class ShippingAddressService {
    private static final Logger logger = LoggerFactory.getLogger(ShippingAddressService.class);
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private ProfileRepository profileRepository;




   /* public ShippingAddressService(ShippingAddressDto shippingAddressDto) {
        this.shippingAddressDto = shippingAddressDto;
    }*/

    public ShippingAddressDto addShippingAddress(ShippingAddressDto dto)
    {

   ShippingAddressEntity shippingAddressEntity = new ShippingAddressEntity();
   shippingAddressEntity.setUserId(dto.getUserId());
   shippingAddressEntity.setFullName(dto.getFullName());
   shippingAddressEntity.setMobileNumber(dto.getMobileNumber());
   shippingAddressEntity.setPincode(dto.getPincode());
   shippingAddressEntity.setCity(dto.getCity());
   shippingAddressEntity.setState(dto.getState());
   shippingAddressEntity.setBuildingName(dto.getBuildingName());
   shippingAddressEntity.setStreetName(dto.getStreetName());
   shippingAddressEntity.setLandmark(dto.getLandmark());
   shippingAddressEntity.setAddressType(dto.getAddressType());
   shippingAddressEntity.setUseDefault(dto.isUseDefault());

        ShippingAddressEntity savedEntity = shippingAddressRepository.save(shippingAddressEntity); // ✅ Save the entity directly*/

        System.out.println("Shipping Address Added Successfully");
        logger.info("Shipping Address Added Successfully");
        return convertToDto(savedEntity);
        //return dto;
    }
    public List<ShippingAddressDto> findAllByUserId(String userId) {
        List<ShippingAddressEntity> addresses = shippingAddressRepository.findAllByUserId(userId);

        return addresses.stream()
                .map(this::convertToDto)
                .toList(); // or .collect(Collectors.toList()) if using Java 8
    }



    private ShippingAddressDto convertToDto(ShippingAddressEntity entity) {
        ShippingAddressDto dto = new ShippingAddressDto();
        dto.setUserId(entity.getUserId());
        dto.setFullName(entity.getFullName());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setPincode(entity.getPincode());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setBuildingName(entity.getBuildingName());
        dto.setStreetName(entity.getStreetName());
        dto.setLandmark(entity.getLandmark());
        dto.setAddressType(entity.getAddressType());
        dto.setUseDefault(entity.isUseDefault());
        return dto;


    }

    /*public ShippingAddressDto updateShippingAddress(ShippingAddressDto dto) {
        //Optional<ShippingAddressEntity> existingOpt = shippingAddressRepository.findById(dto.getId());
        Optional<ShippingAddressEntity> existingOpt;
        // 🔹 Decide which lookup to use
        if (dto.getId() != null) {
            // Case 1: Update by numeric DB id
            existingOpt = shippingAddressRepository.findById(dto.getId());
        }
        else if (dto.getUserId() != null) {
            // Case 2: Update by Firebase UID
            existingOpt = shippingAddressRepository.findByUserId(dto.getUserId()); }
        else { logger.warn("No valid identifier provided (id or userId)");
            return null;
        }
        if (existingOpt.isPresent()) {
            ShippingAddressEntity entity = existingOpt.get();

            // ✅ Update profile fields
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setMobileNumber(dto.getMobileNumber());
            entity.setEmail(dto.getEmail());
          *//*  entity.setBirthDate((dto.getBirthDate() != null) ? LocalDate.parse(dto.getBirthDate().toString()) : null);*//*
            entity.setBirthDate(dto.getBirthDate());
            entity.setGender(dto.getGender());

            // ✅ Update address fields
            //entity.setFullName(dto.getFullName());
            entity.setFullName(dto.getFullName() != null ? dto.getFullName() : entity.getFirstName() + " " + entity.getLastName());
            //entity.setPincode(dto.getPincode());
            if (dto.getPincode() != null) entity.setPincode(dto.getPincode());
            if(dto.getCity() != null) entity.setCity(dto.getCity());
            if(dto.getState() != null) entity.setState(dto.getState());
            if(dto.getBuildingName()!=null) entity.setBuildingName(dto.getBuildingName());
            if(dto.getStreetName() != null) entity.setStreetName(dto.getStreetName());
            if(dto.getLandmark() != null) entity.setLandmark(dto.getLandmark());
           if(dto.getAddressType() != null) entity.setAddressType(dto.getAddressType());
            // ✅ Preserve existing default unless explicitly changed
            if (dto.isUseDefault() != entity.isUseDefault()) {
                entity.setUseDefault(dto.isUseDefault());
            }
           //if(dto.isUseDefault()) entity.setUseDefault(dto.isUseDefault());
            //entity.setUseDefault(dto.isUseDefault());

            // ✅ Save once
            ProfileEntity updatedEntity = ProfileRepository.save(entity);
            logger.info("User Profile + Shipping Address Updated Successfully");
            // ✅ Convert back to DTO return
           return ShippingUserProfileMapper.toDTO(updatedEntity);
        }
        else { logger.warn("No Shipping Address found with id: {} for userId: {}", dto.getId(), dto.getUserId());
            return null;
        }

    }*/


 /*public List<ShippingAddressDto> findAllByUserprofileId(String userId) {
       return shippingAddressRepository.
              findAllByUserId(userId) .
               stream() .
               map(ShippingUserProfileMapper::toDTO) .
               collect(Collectors.toList());
   }*/

}
