package io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefreshAccessTokenCommand {

    private String refreshToken;

    public String refreshToken() {
        return refreshToken;
    }

}
