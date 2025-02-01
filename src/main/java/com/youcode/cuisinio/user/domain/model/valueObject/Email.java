package com.youcode.cuisinio.user.domain.model.valueObject;

import java.util.regex.Pattern;
public record Email(String value) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Adresse email invalide : " + value);
        }
    }

    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}

