package com.filmdb.auth.auth_service.adapter.in.web.mapper;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.application.commands.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestCommandMapperImpl implements  AuthRequestCommandMapper {

    // TODO: Use @NoArgsConstructor for commands, so mapping becomes more evident.

    @Override
    public RegisterUserCommand toRegisterUserCommand(RegisterRequest request) {
        return new RegisterUserCommand(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
    }

    @Override
    public LoginUserCommand toLoginUserCommand(LoginRequest request, RequestContext context) {
        return new LoginUserCommand(
                request.getEmail(),
                request.getPassword(),
                context.getIp(),
                context.getUserAgent()
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

    @Override
    public RefreshAccessTokenCommand toRefreshAccessTokenCommand(RefreshAccessTokenRequest request) {
        return new RefreshAccessTokenCommand(
                request.getRefreshToken()
        );
    }

    @Override
    public OAuthGoogleLoginUserCommand toOAuthGoogleLoginUserCommand(String userGoogleId, String userGoogleEmail,
                                                                     RequestContext context) {
        return new OAuthGoogleLoginUserCommand(
                userGoogleId,
                userGoogleEmail,
                context.getIp(),
                context.getUserAgent()
        );
    }

    @Override
    public OAuthGoogleRegisterUserCommand toOAuthGoogleRegisterUserCommand(String userGoogleId, String userGoogleEmail,
                                                                           RequestContext context) {
        return new OAuthGoogleRegisterUserCommand(
                userGoogleId,
                userGoogleEmail,
                context.getIp(),
                context.getUserAgent()
        );
    }
}
