package com.Perseo.service;

import com.Perseo.model.User;
import com.Perseo.repository.IUserRepository;
import com.Perseo.service.UserService;
import com.Perseo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static com.Perseo.model.ERole.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password")
                .email("test@example.com")
                .role(USER)
                .build();
    }

    @Test
    public void test_SaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void test_Find_By_Username() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testUser");
    }


    @Test
    public void test_Find_By_Id() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void test_SaveUser_Update_Existing_User() {
        User existingUser = User.builder()
                .id(1L)
                .username("existingUser")
                .password("oldPassword")
                .email("old@example.com")
                .role(USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        existingUser.setUsername("updatedUser");
        existingUser.setPassword("newPassword");
        User updatedUser = userService.saveUser(existingUser);

        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("newPassword", updatedUser.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }
    @Test
    public void test_Find_All_Users() {
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(2L).username("user2").build();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }
}
