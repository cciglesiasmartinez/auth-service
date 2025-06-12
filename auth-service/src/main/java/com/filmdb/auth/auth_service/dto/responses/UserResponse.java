package com.filmdb.auth.auth_service.dto.responses;

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

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword()) // Remove before production deployment
                .isAdmin(user.isAdmin())
                .registeredAt(user.getRegisteredAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
