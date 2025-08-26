package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Change username request payload for externally (ie. OAuth2) authenticated users.")
public class ChangeExternalUserUsernameRequest {

    @Schema(
            description = "The desired new username.",
            example = "anej1990"
    )
    @NotBlank(message="Username can't be empty.")
    private String username;

}
