package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.filmdb.auth.auth_service.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String password; // Remove before production
    private boolean isAdmin;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;

    // This should be deleted once migration to DDD + Hex is completed :)
    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword()) // Remove before production deployment
                .registeredAt(user.getRegisteredAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }

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
