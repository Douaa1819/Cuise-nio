package com.youcode.cuisinio.user.application.dto.response;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String firstName,
        String lastName
) { }
