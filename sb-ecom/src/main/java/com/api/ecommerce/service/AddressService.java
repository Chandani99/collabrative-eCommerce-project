package com.api.ecommerce.service;

import com.api.ecommerce.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    public Address saveAddressByUserId(Integer userId, Address address);
    public List<Address> getAddressByUserId(Integer userId);
}
