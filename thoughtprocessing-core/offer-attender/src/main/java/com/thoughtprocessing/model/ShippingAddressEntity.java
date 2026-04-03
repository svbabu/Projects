package com.thoughtprocessing.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "shippingaddrss")
public class ShippingAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // optional if same name
    private Long id;

    @Column(name = "user_id",unique = true, nullable = false)
    private String userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "mobile_number", length = 15)
    private String mobileNumber;

    @Column(name = "pincode", length = 10)
    private String pincode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "use_default")
    private boolean useDefault = false;

    //default constructor
    public ShippingAddressEntity() {

    }
    //parameter constructor
    public ShippingAddressEntity(Long id, String userId,String fullName, String mobileNumber, String pincode, String city, String state, String buildingName, String streetName, String landmark, String addressType, boolean useDefault) {
        this.id = id;
        this.userId=userId;
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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




}
