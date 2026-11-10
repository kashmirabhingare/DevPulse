package com.devpulse.service;

import com.devpulse.dto.ApiResponse;
import com.devpulse.dto.LoginRequest;
import com.devpulse.dto.RegisterRequest;
import com.devpulse.entity.User;
import com.devpulse.exception.ResourceAlreadyExistsException;
import com.devpulse.exception.InvalidCredentialsException;
import com.devpulse.repository.UserRepository;
import com.devpulse.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public ApiResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return new ApiResponse(true, "User Registered Successfully");
    }

    @Override
    public String login(LoginRequest request) {

    	User user = userRepository.findByEmail(request.getEmail())
    	        .orElseThrow(() ->
    	                new InvalidCredentialsException("Invalid email or password"));

    	if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    	    throw new InvalidCredentialsException("Invalid email or password");
    	}

        return jwtService.generateToken(user.getEmail());
    }
}