package com.youcode.cuisenio.features.auth.dto.response;

import com.youcode.cuisenio.features.auth.entity.Role;

import java.time.LocalDateTime;

public record RegisterResponse(
        Long id,
        String username,
        String lastName,
        String email,
        Role role,
        LocalDateTime registrationDate,
        boolean isActive
) {}