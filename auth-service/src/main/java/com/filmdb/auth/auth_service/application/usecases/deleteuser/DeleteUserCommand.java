package com.filmdb.auth.auth_service.application.usecases.deleteuser;

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
