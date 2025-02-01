package com.youcode.cuisinio.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record RegisterUserRequest(
        @NotBlank String email,
        @Size(min = 8) String password,
        String firstName,
        String lastName,
        Date birthDate
) { }