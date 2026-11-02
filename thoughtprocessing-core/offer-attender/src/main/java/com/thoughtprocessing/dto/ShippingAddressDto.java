package com.thoughtprocessing.dto;

import com.thoughtprocessing.model.Order;
import com.thoughtprocessing.model.ShippingAddressEntity;
import java.lang.Boolean;
import java.time.LocalDate;

/*@Data
@NoArgsConstructor
@AllArgsConstructor*/

public class ShippingAddressDto  {


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
    private Boolean useDefault = false;
    private String orderId; // keep it simple
    public ShippingAddressDto() {

    }
    public ShippingAddressDto(String userId,String fullName, String mobileNumber, String pincode,
                              String city, String state, String buildingName, String streetName,
                              String landmark, String addressType, Boolean useDefault, String orderId) {
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
        this.orderId = orderId;
    }

    public ShippingAddressDto(Long id,String addressType,
                              String buildingName, String city,
                              String fullName,String landmark,
                              String mobileNumber,String pincode,
                              String state,String streetName,
                              Boolean useDefault,
                              String userId,String orderId
                              ) {
    this.id = id;
    this.addressType = addressType;
    this.buildingName = buildingName;
    this.city = city;
    this.fullName=fullName;
    this.landmark = landmark;
    this.mobileNumber = mobileNumber;
    this.pincode = pincode;
    this.state = state;
    this.streetName = streetName;
    this.useDefault=useDefault;
    this.userId = userId;
    this.orderId=orderId;
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

    public Boolean isUseDefault() {
        return useDefault;
    }

    public void setUseDefault(Boolean useDefault) {
        this.useDefault = useDefault;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrder(String  orderId) {
        this.orderId = orderId;
    }
//timeline
public static ShippingAddressDto fromEntity(ShippingAddressEntity s) {
    if (s == null) {
        return null; // or return a new ShippingAddressDto() if you prefer an empty object
    }
        return new ShippingAddressDto(
                s.getId(),
                s.getAddressType(),
                s.getBuildingName(),
                s.getCity(),
                s.getFullName(),
                s.getLandmark(),
                s.getMobileNumber(),
                s.getPincode(),
                s.getState(),
                s.getStreetName(),
                s.isUseDefault(),
                s.getUserId(),
                s.getOrderId()



            );

}
}
