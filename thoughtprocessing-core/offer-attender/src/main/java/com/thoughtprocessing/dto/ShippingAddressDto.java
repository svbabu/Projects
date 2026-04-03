package com.thoughtprocessing.dto;

import java.time.LocalDate;

/*@Data
@NoArgsConstructor
@AllArgsConstructor*/

public class ShippingAddressDto {


    private Long id;
    private String userId;
    private String fullName;
    private String mobileNumber;
    private String pincode;
    private String city;
    private String state;
    private String buildingName;
    private String streetName;
    private String landmark;
    private String addressType;
    private boolean useDefault = false;

    public ShippingAddressDto() {

    }
    public ShippingAddressDto(String userId,String fullName, String mobileNumber, String pincode,
                              String city, String state, String buildingName, String streetName,
                              String landmark, String addressType, boolean useDefault) {
        this.userId = userId;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.buildingName = buildingName;
        this.streetName = streetName;
        this.landmark = landmark;
        this.addressType = addressType;
        this.useDefault = useDefault;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public boolean isUseDefault() {
        return useDefault;
    }

    public void setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
