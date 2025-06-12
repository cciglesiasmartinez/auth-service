package com.filmdb.auth.auth_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message="Current password can't be empty.")
    private String currentPassword;

    @NotBlank(message="New password can't be empty.")
    private String newPassword;

}