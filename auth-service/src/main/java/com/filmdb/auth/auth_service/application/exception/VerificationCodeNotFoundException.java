package com.filmdb.auth.auth_service.application.exception;

public class VerificationCodeNotFoundException extends RuntimeException {

    public VerificationCodeNotFoundException() {
        super("Invalid verification code.");
    }

}
