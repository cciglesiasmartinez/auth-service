package com.filmdb.auth.auth_service.adapter.in.web.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChangeEmailRequest {

    @NotBlank(message="Current password can't be empty.")
    private String currentPassword;

    @Email
    @NotBlank(message="Email can't be empty.")
    private String email;

}
