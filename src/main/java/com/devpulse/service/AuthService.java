package com.devpulse.service;

import com.devpulse.dto.ApiResponse;
import com.devpulse.dto.LoginRequest;
import com.devpulse.dto.RegisterRequest;

public interface AuthService {

    ApiResponse register(RegisterRequest request);

    String login(LoginRequest request);
}