package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.*;
import com.filmdb.auth.auth_service.adapter.in.web.mapper.AuthRequestCommandMapper;
import com.filmdb.auth.auth_service.application.commands.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.application.port.in.AuthUseCase;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.port.out.UserLoginRepository;
import com.filmdb.auth.auth_service.infrastructure.security.oauth.GoogleTokenVerifier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final RegisterUserUseCase registerUserService;
    private final VerifyUserRegistrationUseCase verifyUserRegistrationService;
    private final LoginUserUseCase loginUserService;
    private final ChangePasswordUseCase changePasswordService;
    private final ChangeUserUsernameUseCase changeUserUsernameService;
    private final ChangeExternalUserUsernameUseCase changeExternalUserUsernameService;
    private final ChangeUserEmailUseCase changeUserEmailService;
    private final DeleteUserUseCase deleteUserService;
    private final DeleteExternalUserUseCase deleteExternalUserService;
    private final RefreshAccessTokenUseCase refreshAccessTokenService;
    private final OAuthGoogleRegisterUserUseCase oAuthGoogleRegisterService;
    private final OAuthGoogleLoginUserUseCase oAuthGoogleLoginService;
    private final AuthRequestCommandMapper mapper;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final UserLoginRepository userLoginRepository;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        RegisterUserCommand command = mapper.toRegisterUserCommand(request);
        return registerUserService.execute(command);
    }

    @Override
    public UserResponse verifyRegistration(String code) {
        VerifyUserRegistrationCommand command = mapper.toVerifyUserRegistrationCommand(code);
        return verifyUserRegistrationService.execute(command);
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
    public ChangeUsernameResponse changeExternalUserUsername(User user, ChangeExternalUserUsernameRequest request) {
        ChangeExternalUserUsernameCommand command = mapper.toChangeExternalUserUsernameCommand(
                request,
                user.id().value());
        return changeExternalUserUsernameService.execute(command);
    }

    @Override
    public ChangeEmailResponse changeEmail(User user, ChangeEmailRequest request) {
        ChangeUserEmailCommand command = mapper.toChangeUserEmailCommand(request, user.id().value());
        return changeUserEmailService.execute(command);
    }

    @Override
    public LoginResponse OAuthGoogleFlow(OAuthGoogleRequest request, RequestContext context) {
        GoogleTokenVerifier.GoogleUser user = googleTokenVerifier.extractUserInfo(request.getIdToken());
        // TODO: Object for this?
        String userGoogleId = user.googleId();
        String userGoogleEmail = user.email();
        if (userLoginRepository.existsByProviderKey(ProviderKey.of(user.googleId()))) {
            OAuthGoogleLoginUserCommand command = mapper.toOAuthGoogleLoginUserCommand(userGoogleId, userGoogleEmail,
                    context);
            return oAuthGoogleLoginService.execute(command);
        } else {
            OAuthGoogleRegisterUserCommand command = mapper.toOAuthGoogleRegisterUserCommand(userGoogleId,
                    userGoogleEmail, context);
            return oAuthGoogleRegisterService.execute(command);
        }
    }

    @Override
    public void deleteUser(User user, DeleteUserRequest request) {
        DeleteUserCommand command = mapper.toDeleteUserCommand(request, user.id().value());
        deleteUserService.execute(command);
    }

    @Override
    public void deleteExternalUser(User user) {
        DeleteExternalUserCommand command = mapper.toDeleteExternalUserCommand(user.id().value());
        deleteExternalUserService.execute(command);
    }

}
