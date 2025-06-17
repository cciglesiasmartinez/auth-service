package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordResponse {

    private String message;
    private LocalDateTime changedAt;

}
