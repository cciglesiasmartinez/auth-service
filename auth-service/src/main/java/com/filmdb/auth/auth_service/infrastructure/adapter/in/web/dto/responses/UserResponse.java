package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    @Schema(
            description = "",
            example = ""
    )
    private String id;

    @Schema(
            description = "",
            example = ""
    )
    private String username;

    @Schema(
            description = "",
            example = ""
    )
    private String email;

    @Schema(
            description = "",
            example = ""
    )
    private String password; // Remove before production

//    @Schema(
//            description = "",
//            example = ""
//    )
//    private boolean isAdmin;

    @Schema(
            description = "",
            example = ""
    )
    private LocalDateTime registeredAt;

    @Schema(
            description = "",
            example = ""
    )
    private LocalDateTime modifiedAt;

    public static UserResponse fromDomainUser(com.filmdb.auth.auth_service.domain.model.User user) {
        return UserResponse.builder()
                .id(user.id().value())
                .username(user.username().value())
                .email(user.email().value())
                .password(user.password().value())
                .registeredAt(user.registeredAt())
                .modifiedAt(user.modifiedAt())
                .build();
    }
}
