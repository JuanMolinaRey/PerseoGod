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

    @GetMapping("/linkedin")
    public ResponseEntity<String> linkedinLogin() {
        return ResponseEntity.ok("LinkedIn login initiated");
    }

    @GetMapping("/github")
    public ResponseEntity<String> githubLogin() {
        return ResponseEntity.ok("GitHub login initiated");
    }
}
