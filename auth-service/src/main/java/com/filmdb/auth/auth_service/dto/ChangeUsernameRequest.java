package com.filmdb.auth.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChangeUsernameRequest {

    @NotBlank(message="Current password can't be empty.")
    private String currentPassword;

    @NotBlank(message="Username can't be empty.")
    private String username;

}
