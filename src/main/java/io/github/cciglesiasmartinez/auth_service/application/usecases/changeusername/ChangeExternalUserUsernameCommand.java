package io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername;

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
