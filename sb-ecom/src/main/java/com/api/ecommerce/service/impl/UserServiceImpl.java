package com.api.ecommerce.service.impl;

import com.api.ecommerce.model.AppRole;
import com.api.ecommerce.model.Role;
import com.api.ecommerce.model.User;
import com.api.ecommerce.repository.RoleRepository;
import com.api.ecommerce.repository.UserRepository;
import com.api.ecommerce.security.jwt.JwtUtils;
import com.api.ecommerce.security.request.LoginRequest;
import com.api.ecommerce.security.request.SignupRequest;
import com.api.ecommerce.security.response.MessageResponse;
import com.api.ecommerce.security.response.UserInfoResponse;
import com.api.ecommerce.security.service.UserDetailsImpl;
import com.api.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final JwtUtils jwtUtils;



    public UserServiceImpl(JwtUtils jwtUtils
                           ) {
        this.jwtUtils = jwtUtils;

    }



    @Override
    public String currentUserName(Authentication authentication) {
        if (authentication != null)
            return authentication.getName();
        else
            return "";
    }

    @Override
    public UserInfoResponse getUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), roles);
    }

    @Override
    public MessageResponse signOutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return new MessageResponse("You've been signed out!");
    }
}
