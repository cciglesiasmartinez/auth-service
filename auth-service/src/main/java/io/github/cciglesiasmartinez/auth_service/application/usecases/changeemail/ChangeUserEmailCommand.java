package io.github.cciglesiasmartinez.auth_service.application.usecases.changeemail;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeUserEmailCommand {

    private final String userId;
    private final String currentPassword;
    private final String newEmail;

    public String userId() {
        return userId;
    }

    public String currentPassword() {
        return currentPassword;
    }

    public String newEmail() {
        return newEmail;
    }

}
