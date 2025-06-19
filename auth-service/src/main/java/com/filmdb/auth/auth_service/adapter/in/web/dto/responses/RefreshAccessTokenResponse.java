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

    private String jwtToken;
    private LocalDateTime createdAt;

}
