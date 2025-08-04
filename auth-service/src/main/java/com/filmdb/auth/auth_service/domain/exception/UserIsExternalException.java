package com.filmdb.auth.auth_service.domain.exception;

public class UserIsExternalException extends RuntimeException {

    public UserIsExternalException() {
        super("User is externally authenticated.");
    }

}
