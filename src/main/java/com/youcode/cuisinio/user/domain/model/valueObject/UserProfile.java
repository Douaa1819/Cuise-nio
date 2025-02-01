package com.youcode.cuisinio.user.domain.model.valueObject;
import java.util.Date;

public record UserProfile(
        String firstName,
        String lastName,
        Date birthDate
) {
    public UserProfile {
        if (firstName != null && firstName.length() > 50) {
            throw new IllegalArgumentException("Prénom trop long");
        }
    }
}

