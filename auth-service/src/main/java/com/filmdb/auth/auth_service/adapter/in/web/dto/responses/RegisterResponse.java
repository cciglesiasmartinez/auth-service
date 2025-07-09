package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterResponse {

    private String message;
    private String email;

}
