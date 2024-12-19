package com.api.ecommerce.service;

import com.api.ecommerce.model.Address;
import com.api.ecommerce.model.User;
import com.api.ecommerce.payload.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAddressService {

    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressesById(Long addressId);

    List<AddressDTO> getUserAddresses(User user);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);



}
