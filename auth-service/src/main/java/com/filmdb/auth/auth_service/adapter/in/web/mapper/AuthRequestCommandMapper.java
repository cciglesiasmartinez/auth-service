package com.filmdb.auth.auth_service.adapter.in.web.mapper;

import com.filmdb.auth.auth_service.application.commands.*;
import com.filmdb.auth.auth_service.dto.requests.*;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthRequestCommandMapper {

    RegisterUserCommand toRegisterUserCommand(RegisterRequest request);

    LoginUserCommand toLoginUserCommand(LoginRequest request);

    default ChangePasswordCommand toChangePasswordCommand(ChangePasswordRequest request, @Context String userId) {
        return new ChangePasswordCommand(userId, request.getCurrentPassword(), request.getNewPassword());
    }

    default ChangeUserEmailCommand toChangeUserEmailCommand(ChangeEmailRequest request, @Context String userId) {
        return new ChangeUserEmailCommand(userId, request.getCurrentPassword(), request.getEmail());
    }

    default ChangeUserUsernameCommand toChangeUserUsernameCommand(ChangeUsernameRequest request, @Context String userId) {
        return new ChangeUserUsernameCommand(userId, request.getCurrentPassword(), request.getUsername());
    }

    default DeleteUserCommand toDeleteUserCommand(DeleteUserRequest request, @Context String userId) {
        return new DeleteUserCommand(userId, request.getCurrentPassword());
    }

}
