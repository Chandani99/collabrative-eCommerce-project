package com.api.ecommerce.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public class LoginRequest {
    private String username;

    @NotBlank
    private String password;


    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
