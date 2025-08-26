package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Response returned after a successful email change.")
public class ChangeUsernameResponse {

    @Schema(
            description = "New username.",
            example = "anej1990"
    )
    private String newUsername;

    @Schema(
            description = "Timestamp.",
            example = "2025-08-01T23:41:37.3468196"
    )
    private LocalDateTime changedAt;

}
