package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Response returned after a successful email change.")
public class ChangeEmailResponse {

    @Schema(
            description = "New email address.",
            example = "jane@newmail.org"
    )
    private String newEmail;

    @Schema(
            description = "Timestamp.",
            example = "2025-08-01T23:41:37.3468196"
    )
    private LocalDateTime changedAt;

}
