package com.Perseo.controller;

import com.Perseo.model.User;
import com.Perseo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password")
                .email("test@example.com")
                .role("USER")
                .build();
    }

    @Test
    public void testRegister() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"password\",\"email\":\"test@example.com\",\"role\":\"USER\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testGetUser() throws Exception {
        when(userService.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(get("/users/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testGetUser_UserNotFound() throws Exception {
        when(userService.findByUsername("nonExistentUser")).thenThrow(new UsernameNotFoundException("User not found with username: nonExistentUser"));

        mockMvc.perform(get("/users/nonExistentUser"))
                .andExpect(status().isNotFound());
    }
}
