package com.filmdb.auth.auth_service.exceptions;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("Current password doesn't match.");
    }

}
