package io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeUserUsernameCommand {

    private final String userId;
    private final String currentPassword;
    private final String newUsername;

    public String userId() {
        return userId;
    }

    public String currentPassword() {
        return currentPassword;
    }

    public String newUsername() {
        return newUsername;
    }

}
