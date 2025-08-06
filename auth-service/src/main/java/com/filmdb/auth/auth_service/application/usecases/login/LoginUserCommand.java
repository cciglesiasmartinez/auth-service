package com.filmdb.auth.auth_service.application.usecases.login;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUserCommand {

    private final String email;
    private final String password;
    private final String ip;
    private final String userAgent;

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String ip() {
        return ip;
    }

    public String userAgent() {
        return userAgent;
    }

}
