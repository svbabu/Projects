package com.thoughtprocessing.service;

import com.thoughtprocessing.dto.ProfileDTO;
import com.thoughtprocessing.dto.ShippingUserProfileMapper;
import com.thoughtprocessing.model.ProfileEntity;
import com.thoughtprocessing.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO updateProfile(ProfileDTO dto) {
        Optional<ProfileEntity> existingOpt = profileRepository.findByUserId(dto.getUserId());

        if (existingOpt.isPresent()) {
            ProfileEntity entity = existingOpt.get();

            // ✅ Update only profile fields
            if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
            if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
            if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
            if (dto.getMobileNumber() != null) entity.setMobileNumber(dto.getMobileNumber());
            if (dto.getBirthday() != null) entity.setBirthday(dto.getBirthday());
            if (dto.getGender() != null) entity.setGender(dto.getGender());

            ProfileEntity updatedEntity = profileRepository.save(entity);
            return ShippingUserProfileMapper.toDTO(updatedEntity);
        } else {
            // Create new profile if not found
            ProfileEntity newEntity = ShippingUserProfileMapper.toEntity(dto);
            ProfileEntity savedEntity = profileRepository.save(newEntity);
            return ShippingUserProfileMapper.toDTO(savedEntity);
        }
    }
    public List<ProfileDTO> findAllByUserprofileId(String userId) {
        return ProfileRepository.findAllByUserId(userId) .
                stream() .
                map(ShippingUserProfileMapper::toDTO) .
                collect(Collectors.toList());
    }




}
