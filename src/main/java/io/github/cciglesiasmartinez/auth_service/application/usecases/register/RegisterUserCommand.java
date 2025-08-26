package io.github.cciglesiasmartinez.auth_service.application.usecases.register;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterUserCommand {

    private final String username;
    private final String email;
    private final String password;

    public String username() {
        return username;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

}
