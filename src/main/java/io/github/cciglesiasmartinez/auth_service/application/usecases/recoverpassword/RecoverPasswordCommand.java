package io.github.cciglesiasmartinez.auth_service.application.usecases.recoverpassword;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RecoverPasswordCommand {

    private final String email;
    private final String ip;
    private final String userAgent;

    public String email() {
        return email;
    }

    public String ip() {
        return ip;
    }

    public String userAgent() {
        return userAgent;
    }


}
