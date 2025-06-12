package com.filmdb.auth.auth_service.application.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangePasswordCommand {

    private final String userId;
    private final String currentPassword;
    private final String newPassord;

    public String userId() {
        return userId;
    }

    public String currentPassword() {
        return currentPassword;
    }

    public String newPassword() {
        return newPassord;
    }

}
