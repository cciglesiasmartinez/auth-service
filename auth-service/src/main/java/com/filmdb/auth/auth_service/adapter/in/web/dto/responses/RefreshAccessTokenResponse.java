package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshAccessTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private int expiresIn;
    private LocalDateTime issuedAt;

}
