package com.filmdb.auth.auth_service.application.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Email already exists.");
    }

}
