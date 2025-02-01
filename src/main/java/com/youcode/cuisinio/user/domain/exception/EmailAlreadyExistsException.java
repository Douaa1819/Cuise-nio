package com.youcode.cuisinio.user.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("L'email " + email + " est déjà utilisé.");
    }
}