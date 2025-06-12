package com.filmdb.auth.auth_service.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ChangeEmailResponse {

    private String newEmail;
    private LocalDateTime changedAt;

}
