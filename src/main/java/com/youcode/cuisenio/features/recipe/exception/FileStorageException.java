package com.youcode.cuisenio.features.recipe.exception;

import com.youcode.cuisenio.common.exception.base.BusinessException;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message, Throwable code) {
        super(message, code);
    }

    public FileStorageException(String message) {
        super(message);
    }
}

