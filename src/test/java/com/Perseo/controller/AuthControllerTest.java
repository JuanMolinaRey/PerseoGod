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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private AuthResponse authResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
        authResponse = new AuthResponse();
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
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
    public void testLinkedinLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/linkedin"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGithubLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/github"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
