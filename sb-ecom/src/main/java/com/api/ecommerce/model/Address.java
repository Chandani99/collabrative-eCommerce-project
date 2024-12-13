package com.api.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//@Entity
public class Address {

//    @Id
    private  Long addressId;


    private  String city;


    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
