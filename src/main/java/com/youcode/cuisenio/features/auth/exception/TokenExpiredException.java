package com.youcode.cuisenio.features.auth.exception;

public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException() {
        super("Token is expired, please try again to login again");
    }
}
