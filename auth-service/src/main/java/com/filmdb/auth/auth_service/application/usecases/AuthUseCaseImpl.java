package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.*;
import com.filmdb.auth.auth_service.adapter.in.web.mapper.AuthRequestCommandMapper;
import com.filmdb.auth.auth_service.application.commands.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final RegisterUserUseCase registerUserService;
    private final LoginUserUseCase loginUserService;
    private final ChangePasswordUseCase changePasswordService;
    private final ChangeUserUsernameUseCase changeUserUsernameService;
    private final ChangeUserEmailUseCase changeUserEmailService;
    private final DeleteUserUseCase deleteUserService;
    private final RefreshAccessTokenUseCase refreshAccessTokenService;
    private final AuthRequestCommandMapper mapper;

    @Override
    public UserResponse register(RegisterRequest request) {
        RegisterUserCommand command = mapper.toRegisterUserCommand(request);
        return registerUserService.execute(command);
    }

    @Override
    public LoginResponse login(LoginRequest request, RequestContext context) {
        LoginUserCommand command = mapper.toLoginUserCommand(request, context);
        return loginUserService.execute(command);
    }

    @Override
    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request) {
        RefreshAccessTokenCommand command = mapper.toRefreshAccessTokenCommand(request);
        return refreshAccessTokenService.execute(command);
    }

    @Override
    public ChangePasswordResponse changePassword(User user, ChangePasswordRequest request) {
        ChangePasswordCommand command = mapper.toChangePasswordCommand(request, user.id().value());
        return changePasswordService.execute(command);
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
