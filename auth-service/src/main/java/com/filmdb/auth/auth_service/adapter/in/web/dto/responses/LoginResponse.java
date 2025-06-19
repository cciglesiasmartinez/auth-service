package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;
    private String tokenType = "Bearer";
    private String refreshToken;
    private long expiresIn;
    private String username;

    public LoginResponse(String token, String refreshToken, long expiresIn, String username) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.username = username;
    }

}
