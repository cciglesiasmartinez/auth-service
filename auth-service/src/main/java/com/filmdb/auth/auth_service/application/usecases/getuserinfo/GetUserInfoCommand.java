package com.filmdb.auth.auth_service.application.usecases.getuserinfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUserInfoCommand {

    private final String userId;

    public String userId() {
        return userId;
    }

}
