package com.youcode.cuisinio.shared.exception;

import com.youcode.cuisinio.user.domain.exception.EmailAlreadyExistsException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }
}

