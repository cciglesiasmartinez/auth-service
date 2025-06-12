package com.filmdb.auth.auth_service.application.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUserCommand {

    private final String email;
    private final String password;

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

}
