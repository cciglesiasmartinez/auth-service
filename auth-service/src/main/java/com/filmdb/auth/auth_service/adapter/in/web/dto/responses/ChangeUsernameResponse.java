package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
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
