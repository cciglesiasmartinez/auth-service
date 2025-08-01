package com.filmdb.auth.auth_service.adapter.in.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Change username request payload for externally (ie. OAuth2) authenticated users.")
public class ChangeExternalUserUsernameRequest {

    @Schema(
            description = "The desired new username.",
            example = "anej1990"
    )
    @NotBlank(message="Username can't be empty.")
    private String username;

}
