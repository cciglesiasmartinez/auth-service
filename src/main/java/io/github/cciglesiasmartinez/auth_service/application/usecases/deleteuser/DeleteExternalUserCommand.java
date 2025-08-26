package io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteExternalUserCommand {

    private final String userId;

    public String userId() {
        return userId;
    }

}
