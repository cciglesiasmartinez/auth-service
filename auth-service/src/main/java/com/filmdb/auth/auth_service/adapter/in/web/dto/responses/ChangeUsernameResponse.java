package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ChangeUsernameResponse {

    private String newUsername;
    private LocalDateTime changedAt;

}
