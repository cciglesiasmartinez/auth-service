package io.github.cciglesiasmartinez.auth_service.application.usecases.resetpassword;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResetPasswordCommand {

    private final String recoverCode;
    private final String email;
    private final String password;
    private final String ip;
    private final String userAgent;

    public String recoverCode() {
        return recoverCode;
    }

    public String email() {
        return email;
    }

    public String password() { return password; }

    public String ip() {
        return ip;
    }

    public String userAgent() {
        return userAgent;
    }

}
