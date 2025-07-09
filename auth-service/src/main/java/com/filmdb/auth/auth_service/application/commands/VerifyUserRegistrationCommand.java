package com.filmdb.auth.auth_service.application.commands;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VerifyUserRegistrationCommand {

    private final String verificationCode;

    public String verificationCode() {
        return this.verificationCode;
    }

}
