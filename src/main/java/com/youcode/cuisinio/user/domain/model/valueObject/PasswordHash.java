package com.youcode.cuisinio.user.domain.model.valueObject;

public record PasswordHash(String value) {
    public PasswordHash {
        if (value == null || value.length() < 60) {
            throw new IllegalArgumentException("Hash invalide");
        }
    }
}