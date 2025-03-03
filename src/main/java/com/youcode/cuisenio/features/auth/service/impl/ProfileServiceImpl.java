package com.youcode.cuisenio.features.auth.service.impl;

import com.youcode.cuisenio.features.auth.dto.profile.request.UpdatePasswordRequest;
import com.youcode.cuisenio.features.auth.dto.profile.request.UpdateProfileRequest;
import com.youcode.cuisenio.features.auth.dto.profile.response.ProfileResponse;
import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.mapper.UserMapper;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.auth.service.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ProfileServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public ProfileResponse getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return userMapper.userToProfileResponse(user);
    }

    public ProfileResponse updateProfile(Authentication authentication, UpdateProfileRequest request) {
        User user = (User) authentication.getPrincipal();
        user.setUsername(request.username());
        user.setLastName(request.lastName());
        userRepository.save(user);
        return userMapper.userToProfileResponse(user);
    }

    public void updatePassword(Authentication authentication, UpdatePasswordRequest request) {
        User user = (User) authentication.getPrincipal();
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public void deleteAccount(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userRepository.delete(user);
    }
}