package io.github.cciglesiasmartinez.auth_service.application.usecases.logout;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogoutUserCommand {

    private final String ip;
    private final String userAgent;
    private final String language;
    private final String refreshToken;

    public String ip() {
        return this.ip;
    }

    public String userAgent() {
        return this.userAgent;
    }

    public String language() {
        return this.language;
    }

    public String refreshToken() {
        return this.refreshToken;
    }

}
