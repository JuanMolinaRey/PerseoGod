package com.Perseo.controller;

import com.Perseo.dtos.request.LoginRequest;
import com.Perseo.dtos.request.RegisterRequest;
import com.Perseo.dtos.response.AuthResponse;
import com.Perseo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private AuthResponse authResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
        authResponse = new AuthResponse();
    }

    @Test
    public void test_Login() {
        when(authService.login(loginRequest)).thenReturn(authResponse);
        ResponseEntity<AuthResponse> result = authController.login(loginRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(authResponse, result.getBody());
    }

    @Test
    public void test_Register() {
        when(authService.register(registerRequest)).thenReturn(authResponse);
        ResponseEntity<AuthResponse> result = authController.register(registerRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(authResponse, result.getBody());
    }

    @Test
    public void test_Linkedin_Login() {
        ResponseEntity<AuthResponse> result = authController.linkedinLogin(loginRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void test_Github_Login() {
        ResponseEntity<AuthResponse> result = authController.githubLogin(loginRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
