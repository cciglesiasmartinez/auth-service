package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "Response returned after a successful registration.")
public class RegisterResponse {

    @Schema(
            description = "",
            example = ""
    )
    private String message;

    @Schema(
            description = "",
            example = ""
    )
    private String email;

}
