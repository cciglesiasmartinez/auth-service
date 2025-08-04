package com.filmdb.auth.auth_service.domain.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException() {
        super("Username or email already registered.");
    }

}
