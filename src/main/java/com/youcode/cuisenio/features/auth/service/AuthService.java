package com.youcode.cuisenio.features.auth.service;

import com.youcode.cuisenio.features.auth.dto.request.LoginRequest;
import com.youcode.cuisenio.features.auth.dto.request.RegisterRequest;
import com.youcode.cuisenio.features.auth.dto.response.LoginResponse;
import com.youcode.cuisenio.features.auth.dto.response.RegisterResponse;
import com.youcode.cuisenio.features.auth.entity.Role;
import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.exception.AuthenticationException;
import com.youcode.cuisenio.features.auth.mapper.UserMapper;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.auth.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface AuthService {

    public RegisterResponse register(RegisterRequest request);
    public LoginResponse login(LoginRequest request);
}
