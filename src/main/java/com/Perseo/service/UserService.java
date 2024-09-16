package com.Perseo.service;

import com.Perseo.model.User;
import com.Perseo.repository.IUserRepository;
import com.Perseo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserRepository iUserRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.iUserRepository = userRepository;
    }

    public User saveUser(User user) {
        return iUserRepository.save(user);
    }

    public User findByUsername(String username) {
        return iUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User findById(Long id) {
        return iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());

        return iUserRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (!iUserRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }

        iUserRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        return iUserRepository.findAll();
    }
}
