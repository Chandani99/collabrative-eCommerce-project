package com.api.ecommerce.service;

import com.api.ecommerce.security.response.MessageResponse;
import com.api.ecommerce.security.response.UserInfoResponse;
import org.springframework.security.core.Authentication;

public interface IUserService {


    public String currentUserName(Authentication authentication);

    public UserInfoResponse getUserDetails(Authentication authentication);

    public MessageResponse signOutUser();


}
