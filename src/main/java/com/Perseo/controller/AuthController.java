package com.Perseo.controller;

import com.Perseo.dtos.request.LoginRequest;
import com.Perseo.dtos.request.RegisterRequest;
import com.Perseo.dtos.response.AuthResponse;
import com.Perseo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/linkedin")
    public ResponseEntity<AuthResponse> linkedinLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/github")
    public ResponseEntity<AuthResponse> githubLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().build();
    }
}
