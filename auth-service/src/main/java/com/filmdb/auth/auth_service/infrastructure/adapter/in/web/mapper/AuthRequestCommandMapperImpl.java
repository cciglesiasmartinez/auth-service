package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.mapper;

import com.filmdb.auth.auth_service.application.usecases.getuserinfo.GetUserInfoCommand;
import com.filmdb.auth.auth_service.application.usecases.recoverpassword.RecoverPasswordCommand;
import com.filmdb.auth.auth_service.application.usecases.resetpassword.ResetPasswordCommand;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.application.usecases.changeemail.ChangeUserEmailCommand;
import com.filmdb.auth.auth_service.application.usecases.changepassword.ChangePasswordCommand;
import com.filmdb.auth.auth_service.application.usecases.changeusername.ChangeExternalUserUsernameCommand;
import com.filmdb.auth.auth_service.application.usecases.changeusername.ChangeUserUsernameCommand;
import com.filmdb.auth.auth_service.application.usecases.deleteuser.DeleteExternalUserCommand;
import com.filmdb.auth.auth_service.application.usecases.deleteuser.DeleteUserCommand;
import com.filmdb.auth.auth_service.application.usecases.login.LoginUserCommand;
import com.filmdb.auth.auth_service.application.usecases.logingoogle.OAuthGoogleLoginUserCommand;
import com.filmdb.auth.auth_service.application.usecases.refreshtoken.RefreshAccessTokenCommand;
import com.filmdb.auth.auth_service.application.usecases.register.RegisterUserCommand;
import com.filmdb.auth.auth_service.application.usecases.registergoogle.OAuthGoogleRegisterUserCommand;
import com.filmdb.auth.auth_service.application.usecases.verifyregistration.VerifyUserRegistrationCommand;
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
    public VerifyUserRegistrationCommand toVerifyUserRegistrationCommand(String code) {
        return new VerifyUserRegistrationCommand(code);
    }

    @Override
    public RecoverPasswordCommand toRecoverPasswordCommand(RecoverPasswordRequest request, RequestContext context) {
        return new RecoverPasswordCommand(
                request.getEmail(),
                context.getIp(),
                context.getUserAgent()
        );
    }

    @Override
    public ResetPasswordCommand toResetPasswordCommand(ResetPasswordRequest request, RequestContext context) {
        return new ResetPasswordCommand(
                request.getRecoverCode(),
                request.getEmail(),
                request.getPassword(),
                context.getIp(),
                context.getUserAgent()
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
    public ChangeExternalUserUsernameCommand toChangeExternalUserUsernameCommand(ChangeExternalUserUsernameRequest request, String userId) {
        return new ChangeExternalUserUsernameCommand(
                userId,
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
    public DeleteExternalUserCommand toDeleteExternalUserCommand(String userId) {
        return new DeleteExternalUserCommand(userId);
    }

    @Override
    public RefreshAccessTokenCommand toRefreshAccessTokenCommand(RefreshAccessTokenRequest request) {
        return new RefreshAccessTokenCommand(
                request.getRefreshToken()
        );
    }

    @Override
    public GetUserInfoCommand toGetUserInfoCommand(String userId) {
        return new GetUserInfoCommand(userId);
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
