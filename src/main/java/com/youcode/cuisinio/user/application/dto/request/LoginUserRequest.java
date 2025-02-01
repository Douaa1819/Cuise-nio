package com.youcode.cuisinio.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank String email,
        @NotBlank String password
) { }
