package com.api.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    public Address saveAddress();
    public List<Address> getAddressByUserId(Integer userId);
    public String deleteAddress(Integer userId, Integer addressId);
}
