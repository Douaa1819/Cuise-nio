package com.youcode.cuisenio.features.auth.service.impl;

import com.youcode.cuisenio.features.auth.dto.request.LoginRequest;
import com.youcode.cuisenio.features.auth.dto.request.RegisterRequest;
import com.youcode.cuisenio.features.auth.dto.response.LoginResponse;
import com.youcode.cuisenio.features.auth.dto.response.RegisterResponse;
import com.youcode.cuisenio.features.auth.entity.Role;
import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.exception.AuthenticationException;
import com.youcode.cuisenio.features.auth.mapper.UserMapper;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.auth.service.AuthService;
import com.youcode.cuisenio.features.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AuthenticationException("Email déjà utilisé");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new AuthenticationException("Nom d'utilisateur déjà utilisé");
        }

        User user = userMapper.registerRequestToUser(request);
        if (user.getRole() == null) {
            user.setRole(Role.getDefault());
        }

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRegistrationDate(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);
        return userMapper.userToRegisterResponse(user);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Email ou mot de passe incorrect");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String token = jwtUtil.generateToken(userDetails);
        return new LoginResponse(token);
    }
}
