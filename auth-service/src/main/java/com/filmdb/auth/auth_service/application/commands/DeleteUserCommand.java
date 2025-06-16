package com.filmdb.auth.auth_service.application.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUserCommand {

    private final String userId;
    private final String currentPassword;

    public String userId() {
        return userId;
    }

    public String currentPassword() {
        return currentPassword;
    }

}
