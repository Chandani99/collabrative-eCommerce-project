package com.api.ecommerce.service;

import com.api.ecommerce.security.request.LoginRequest;
import com.api.ecommerce.security.request.SignupRequest;
import com.api.ecommerce.security.response.MessageResponse;
import com.api.ecommerce.security.response.UserInfoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {






    public String currentUserName(Authentication authentication);

    public UserInfoResponse getUserDetails(Authentication authentication);

    public MessageResponse signOutUser();


}
