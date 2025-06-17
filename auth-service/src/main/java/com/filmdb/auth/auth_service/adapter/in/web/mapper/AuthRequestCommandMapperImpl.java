package com.filmdb.auth.auth_service.adapter.in.web.mapper;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.application.commands.*;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestCommandMapperImpl implements  AuthRequestCommandMapper {
    @Override
    public RegisterUserCommand toRegisterUserCommand(RegisterRequest request) {
        return new RegisterUserCommand(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );
    }

    @Override
    public LoginUserCommand toLoginUserCommand(LoginRequest request) {
        return new LoginUserCommand(
                request.getEmail(),
                request.getPassword()
        );
    }

    @Override
    public ChangePasswordCommand toChangePasswordCommand(ChangePasswordRequest request, String userId) {
        return new ChangePasswordCommand(
                userId,
                request.getCurrentPassword(),
                request.getNewPassword()
        );
    }

    @Override
    public ChangeUserEmailCommand toChangeUserEmailCommand(ChangeEmailRequest request, String userId) {
        return new ChangeUserEmailCommand(
                userId,
                request.getCurrentPassword(),
                request.getEmail()
        );
    }

    @Override
    public ChangeUserUsernameCommand toChangeUserUsernameCommand(ChangeUsernameRequest request, String userId) {
        return new ChangeUserUsernameCommand(
                userId,
                request.getCurrentPassword(),
                request.getUsername()
        );
    }

    @Override
    public DeleteUserCommand toDeleteUserCommand(DeleteUserRequest request, String userId) {
        return new DeleteUserCommand(
                userId,
                request.getCurrentPassword()
        );
    }
}
