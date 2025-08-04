package com.filmdb.auth.auth_service.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Email already exists.");
    }

}
