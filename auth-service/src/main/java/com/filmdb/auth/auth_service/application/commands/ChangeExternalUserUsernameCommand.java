package com.filmdb.auth.auth_service.application.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeExternalUserUsernameCommand {

    private final String userId;
    private final String newUsername;

    public String userId() {
        return userId;
    }

    public String newUsername() {
        return newUsername;
    }

}
