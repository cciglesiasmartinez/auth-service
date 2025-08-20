package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Recovery password request payload.")
public class RecoverPasswordRequest {

    @Schema(
            description = "User email.",
            example = "jane@doe.is"
    )
    @NotBlank(message="Email can't be empty.")
    @Email
    private String email;

}
