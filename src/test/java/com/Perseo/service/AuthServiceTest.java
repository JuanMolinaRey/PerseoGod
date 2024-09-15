package com.Perseo.service;

import com.Perseo.dtos.request.LoginRequest;
import com.Perseo.dtos.request.RegisterRequest;
import com.Perseo.dtos.response.AuthResponse;
import com.Perseo.model.ERole;
import com.Perseo.model.User;
import com.Perseo.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtService jwtService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    private User user;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .username("john")
                .password("password")
                .role(ERole.USER)
                .build();

        loginRequest = new LoginRequest();
        loginRequest.setUsername("john");
        loginRequest.setPassword("password");

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("john");
        registerRequest.setEmail("john@example.com");
        registerRequest.setPassword("password");
        registerRequest.setRole(ERole.USER);
    }

    @Test
    public void testLoginSuccess() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(jwtService.getTokenService(any(UserDetails.class))).thenReturn("token123");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(ERole.USER, response.getRole());
    }

    @Test
    public void testRegister() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.getTokenService(any(User.class))).thenReturn("token123");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals(ERole.USER, response.getRole());
    }
}
