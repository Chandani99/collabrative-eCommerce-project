package com.api.ecommerce.controller;

import com.api.ecommerce.security.jwt.JwtUtils;
import com.api.ecommerce.security.request.LoginRequest;
import com.api.ecommerce.security.response.UserInfoResponse;
import com.api.ecommerce.security.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testAuthenticateUser_Success() {
        // Mock input
        LoginRequest loginRequest = new LoginRequest("testUser", "password123");

        // Mock authentication
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_USER";
            }
        };

        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testUser", "xyz@gmail.com", "password@123", List.of(grantedAuthority));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock JWT Cookie
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "mocked-token").build();
        when(jwtUtils.generateJwtCookie(userDetails)).thenReturn(jwtCookie);

        // Execute method
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        // Verify behavior
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey(HttpHeaders.SET_COOKIE));
        assertEquals(jwtCookie.toString(), response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));

        UserInfoResponse responseBody = (UserInfoResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals("testUser", responseBody.getUsername());
    }

    @Test
    void testAuthenticateUser_Failure() {
        // Mock input
        LoginRequest loginRequest = new LoginRequest("invalidUser", "wrongPassword");

        // Mock failure
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        // Execute method
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        // Verify response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("Bad credentials", responseBody.get("message"));
        assertEquals(false, responseBody.get("status"));
    }
}
