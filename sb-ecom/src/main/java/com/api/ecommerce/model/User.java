package com.api.ecommerce.model;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class User {


    private Long id;
    private String userName;
    private String email;
    private String password;

    private List<Address> address;



}
