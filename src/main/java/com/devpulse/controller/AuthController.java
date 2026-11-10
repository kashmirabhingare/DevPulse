package com.devpulse.controller;

import com.devpulse.dto.ApiResponse;
import com.devpulse.dto.AuthResponse;
import com.devpulse.dto.LoginRequest;
import com.devpulse.dto.RegisterRequest;
import com.devpulse.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return new AuthResponse(token, "Login Successful");
    }
}