package com.filmdb.auth.auth_service.adapter.in.web.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RefreshAccessTokenRequest {

    @NotBlank(message = "Refresh token cannot be empty.")
    private String refreshToken;

}
