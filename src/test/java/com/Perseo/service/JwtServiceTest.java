package com.Perseo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
    }

    @Test
    public void testGetTokenService() {
        UserDetails user = User.withUsername("testUser").password("password").roles("USER").build();
        String token = jwtService.getTokenService(user);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // JWT tokens start with "eyJ"
    }

    @Test
    public void testGetUsernameFromToken() {
        UserDetails user = User.withUsername("testUser").password("password").roles("USER").build();
        String token = jwtService.getTokenService(user);

        String username = jwtService.getUsernameFromToken(token);

        assertEquals("testUser", username);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails user = User.withUsername("testUser").password("password").roles("USER").build();
        String token = jwtService.getTokenService(user);

        boolean isValid = jwtService.isTokenValid(token, user);

        assertTrue(isValid);
    }

    @Test
    public void testIsTokenExpired() {
        UserDetails user = User.withUsername("testUser").password("password").roles("USER").build();
        String token = jwtService.getTokenService(user);

        boolean isExpired = jwtService.isTokenExpired(token);

        assertFalse(isExpired);
    }
}
