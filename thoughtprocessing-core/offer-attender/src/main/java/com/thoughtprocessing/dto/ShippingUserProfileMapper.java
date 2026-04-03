package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.ProfileEntity;


import java.time.LocalDate;

public class ShippingUserProfileMapper {
    public static ProfileDTO toDTO(ProfileEntity entity)
    {
        ProfileDTO dto = new ProfileDTO();
        dto.setUserId(entity.getUserId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setMobileNumber(entity.getMobileNumber());
        dto.setEmail(entity.getEmail());
        /*dto.setBirthDate(entity.getBirthDate() != null ? LocalDate.parse(entity.getBirthDate().toString()) : null);*/
        dto.setBirthday(entity.getBirthday());
        dto.setGender(entity.getGender());
        return dto;
    }

    public static ProfileEntity toEntity(ProfileDTO dto) {
        return new ProfileEntity(
                dto.getUserId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getMobileNumber(),
                dto.getEmail(),
                dto.getBirthday(),
                dto.getGender() );
    }
}
