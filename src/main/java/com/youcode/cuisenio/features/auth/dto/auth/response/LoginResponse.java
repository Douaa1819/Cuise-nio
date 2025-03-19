package com.youcode.cuisenio.features.auth.dto.auth.response;


import com.youcode.cuisenio.features.auth.entity.Role;

public record LoginResponse(
            String token,
            Long userId,
            String email,
            String username,
            Role role
) {}
