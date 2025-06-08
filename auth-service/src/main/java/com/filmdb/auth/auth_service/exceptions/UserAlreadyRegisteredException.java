package com.filmdb.auth.auth_service.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException() {
        super("Username or email already registered.");
    }

}
