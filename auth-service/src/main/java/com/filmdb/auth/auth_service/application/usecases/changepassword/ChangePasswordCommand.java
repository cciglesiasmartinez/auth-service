package com.filmdb.auth.auth_service.application.usecases.changepassword;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangePasswordCommand {

    private final String userId;
    private final String currentPassword;
    private final String newPassword;

    public String userId() {
        return userId;
    }

    public String currentPassword() {
        return currentPassword;
    }

    public String newPassword() {
        return newPassword;
    }

}
