package com.api.ecommerce.controller;

//public class AddressControllerTest {
//}

//package com.api.ecommerce.controller;

import com.api.ecommerce.model.User;
import com.api.ecommerce.payload.AddressDTO;
import com.api.ecommerce.service.IAddressService;
import com.api.ecommerce.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AuthUtil authUtil;

    @Mock
    private IAddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private User user;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        addressDTO = new AddressDTO();
        addressDTO.setAddressId(1L);
        addressDTO.setStreet("123 Main St");
        addressDTO.setCity("Test City");
        addressDTO.setState("TS");
        addressDTO.setPincode("12345");
        addressDTO.setBuildingName("Bajaj");
    }

    @Test
    void testCreateAddress() {
        when(authUtil.loggedInUser()).thenReturn(user);
        when(addressService.createAddress(addressDTO, user)).thenReturn(addressDTO);

        ResponseEntity<AddressDTO> response = addressController.createAddress(addressDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(addressDTO, response.getBody());
        verify(authUtil, times(1)).loggedInUser();
        verify(addressService, times(1)).createAddress(addressDTO, user);
    }

    @Test
    void testGetAddresses() {
        List<AddressDTO> addressList = Arrays.asList(addressDTO);
        when(addressService.getAddresses()).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressController.getAddresses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressList, response.getBody());
        verify(addressService, times(1)).getAddresses();
    }

    @Test
    void testGetAddressById() {
        Long addressId = 1L;
        when(addressService.getAddressesById(addressId)).thenReturn(addressDTO);

        ResponseEntity<AddressDTO> response = addressController.getAddressById(addressId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressDTO, response.getBody());
        verify(addressService, times(1)).getAddressesById(addressId);
    }

    @Test
    void testGetUserAddresses() {
        List<AddressDTO> addressList = Arrays.asList(addressDTO);
        when(authUtil.loggedInUser()).thenReturn(user);
        when(addressService.getUserAddresses(user)).thenReturn(addressList);

        ResponseEntity<List<AddressDTO>> response = addressController.getUserAddresses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressList, response.getBody());
        verify(authUtil, times(1)).loggedInUser();
        verify(addressService, times(1)).getUserAddresses(user);
    }

    @Test
    void testUpdateAddress() {
        Long addressId = 1L;
        when(addressService.updateAddress(addressId, addressDTO)).thenReturn(addressDTO);

        ResponseEntity<AddressDTO> response = addressController.updateAddress(addressId, addressDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressDTO, response.getBody());
        verify(addressService, times(1)).updateAddress(addressId, addressDTO);
    }

    @Test
    void testDeleteAddress() {
        Long addressId = 1L;
        String status = "Address deleted successfully";
        when(addressService.deleteAddress(addressId)).thenReturn(status);

        ResponseEntity<String> response = addressController.updateAddress(addressId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());
        verify(addressService, times(1)).deleteAddress(addressId);
    }
}
