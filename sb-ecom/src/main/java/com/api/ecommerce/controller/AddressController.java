package com.api.ecommerce.controller;


import com.api.ecommerce.exception.APIException;
import com.api.ecommerce.model.User;
import com.api.ecommerce.payload.AddressDTO;
import com.api.ecommerce.service.IAddressService;
import com.api.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Address",
        description = "Address API"
)
@RestController
@RequestMapping("/api")
public class AddressController {

    private final AuthUtil authUtil;
    private final IAddressService addressService;

    public AddressController(AuthUtil authUtil, IAddressService addressService) {
        this.authUtil = authUtil;
        this.addressService = addressService;
    }


    @Operation(summary = "Create a new address", description = "Creates a new address for the logged-in user and returns the saved address details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address created successfully",
                    content = @Content(
                    schema = @Schema(implementation = AddressDTO.class)
            )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data" ,
                    content = @Content(
                            schema = @Schema(implementation = APIException.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not logged in",   content = @Content(
                    schema = @Schema(implementation = APIException.class)
            )),
            @ApiResponse(responseCode = "500", description = "Internal server error",   content = @Content(
                    schema = @Schema(implementation = APIException.class)
            ))
    })
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }


    @Operation(summary = "Get all addresses", description = "Retrieves a list of all saved addresses."

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of addresses retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not logged in"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressList = addressService.getAddresses();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @Operation(summary = "Get address by ID", description = "Retrieves a specific address based on the provided ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not logged in"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressesById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }


    @Operation(summary = "Get addresses for the logged-in user",
            description = "Retrieves a list of addresses associated with the currently authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User addresses retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not logged in"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressList = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @Operation(summary = "Update an address", description = "Updates an existing address based on the provided ID and returns the updated address details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not logged in"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId
            , @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @Operation(summary = "Delete an address", description = "Deletes an address based on the provided ID and returns a success message.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not logged in"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
