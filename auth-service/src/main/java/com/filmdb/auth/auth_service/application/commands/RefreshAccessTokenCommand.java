package com.filmdb.auth.auth_service.application.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RefreshAccessTokenCommand {

    private String refreshToken;

    public String refreshToken() {
        return refreshToken;
    }

}
