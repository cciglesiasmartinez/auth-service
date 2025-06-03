package com.filmdb.auth.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @Email
    @NotBlank(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private boolean isAdmin;

}
