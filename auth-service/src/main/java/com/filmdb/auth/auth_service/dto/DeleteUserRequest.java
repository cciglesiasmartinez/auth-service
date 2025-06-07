package com.filmdb.auth.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DeleteUserRequest {

    @NotBlank(message="Current password can't be empty.")
    private String currentPassword;

}
