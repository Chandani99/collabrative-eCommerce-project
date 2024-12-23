package com.api.ecommerce.model;


import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank

    @Size(min = 5, message = "Street name must be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Building name must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 4, message = "City name must be least 4 characters")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State name must be least 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country name must be least 2 characters")
    private String country;

    @NotBlank
    @Size(min = 5, message = "Pincode must be atleast 5 characters")
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
