package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.adapter.in.web.mapper.AuthRequestCommandMapper;
import com.filmdb.auth.auth_service.application.commands.*;
import com.filmdb.auth.auth_service.application.usecase.AuthUseCase;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.dto.requests.*;
import com.filmdb.auth.auth_service.dto.responses.ChangeEmailResponse;
import com.filmdb.auth.auth_service.dto.responses.ChangeUsernameResponse;
import com.filmdb.auth.auth_service.dto.responses.LoginResponse;
import com.filmdb.auth.auth_service.dto.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final RegisterUserService registerUserService;
    private final LoginUserService loginUserService;
    private final ChangePasswordService changePasswordService;
    private final ChangeUserUsernameService changeUserUsernameService;
    private final ChangeUserEmailService changeUserEmailService;
    private final DeleteUserService deleteUserService;
    private final AuthRequestCommandMapper mapper;

    @Override
    public UserResponse register(RegisterRequest request) {
        RegisterUserCommand command = mapper.toRegisterUserCommand(request);
        return registerUserService.execute(command);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginUserCommand command = mapper.toLoginUserCommand(request);
        return loginUserService.execute(command);
    }

    @Override
    public void changePassword(User user, ChangePasswordRequest request) {
        ChangePasswordCommand command = mapper.toChangePasswordCommand(request, user.id().value());
        changePasswordService.execute(command);
    }

    @Override
    public ChangeUsernameResponse changeUsername(User user, ChangeUsernameRequest request) {
        ChangeUserUsernameCommand command = mapper.toChangeUserUsernameCommand(request, user.id().value());
        return changeUserUsernameService.execute(command);
    }

    @Override
    public ChangeEmailResponse changeEmail(User user, ChangeEmailRequest request) {
        ChangeUserEmailCommand command = mapper.toChangeUserEmailCommand(request, user.id().value());
        return changeUserEmailService.execute(command);

    }

    @Override
    public void deleteUser(User user, DeleteUserRequest request) {
        DeleteUserCommand command = mapper.toDeleteUserCommand(request, user.id().value());
        deleteUserService.execute(command);
    }

}
