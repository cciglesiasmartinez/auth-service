package io.github.cciglesiasmartinez.auth_service.application.usecases;

import io.github.cciglesiasmartinez.auth_service.application.dto.LoginResult;
import io.github.cciglesiasmartinez.auth_service.application.usecases.getuserinfo.GetUserInfoCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.getuserinfo.GetUserInfoUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.logout.LogoutUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.logout.LogoutUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.recoverpassword.RecoverPasswordCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.recoverpassword.RecoverPasswordUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.resetpassword.ResetPasswordCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.resetpassword.ResetPasswordUseCase;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests.*;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.*;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.mapper.AuthRequestCommandMapper;
import io.github.cciglesiasmartinez.auth_service.application.context.RequestContext;
import io.github.cciglesiasmartinez.auth_service.application.port.in.AuthUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeemail.ChangeUserEmailCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeemail.ChangeUserEmailUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changepassword.ChangePasswordCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changepassword.ChangePasswordUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername.ChangeExternalUserUsernameCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername.ChangeExternalUserUsernameUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername.ChangeUserUsernameCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername.ChangeUserUsernameUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser.DeleteExternalUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser.DeleteExternalUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser.DeleteUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser.DeleteUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.login.LoginUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.login.LoginUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.logingoogle.OAuthGoogleLoginUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.logingoogle.OAuthGoogleLoginUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken.RefreshAccessTokenCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken.RefreshAccessTokenUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.register.RegisterUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.register.RegisterUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.registergoogle.OAuthGoogleRegisterUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.registergoogle.OAuthGoogleRegisterUserUseCase;
import io.github.cciglesiasmartinez.auth_service.application.usecases.verifyregistration.VerifyUserRegistrationCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.verifyregistration.VerifyUserRegistrationUseCase;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.ProviderKey;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserLoginRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.security.oauth.GoogleTokenVerifier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final RegisterUserUseCase registerUserService;
    private final VerifyUserRegistrationUseCase verifyUserRegistrationService;
    private final RecoverPasswordUseCase recoverPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final LoginUserUseCase loginUserService;
    private final ChangePasswordUseCase changePasswordService;
    private final ChangeUserUsernameUseCase changeUserUsernameService;
    private final ChangeExternalUserUsernameUseCase changeExternalUserUsernameService;
    private final ChangeUserEmailUseCase changeUserEmailService;
    private final DeleteUserUseCase deleteUserService;
    private final DeleteExternalUserUseCase deleteExternalUserService;
    private final RefreshAccessTokenUseCase refreshAccessTokenService;
    private final LogoutUserUseCase logoutUserUseCase;
    private final OAuthGoogleRegisterUserUseCase oAuthGoogleRegisterService;
    private final OAuthGoogleLoginUserUseCase oAuthGoogleLoginService;
    private final GetUserInfoUseCase getUserInfoUseCase;
    private final AuthRequestCommandMapper mapper;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final UserLoginRepository userLoginRepository;

    @Override
    public Envelope<RegisterResponse> register(RegisterRequest request) {
        RegisterUserCommand command = mapper.toRegisterUserCommand(request);
        return registerUserService.execute(command);
    }

    @Override
    public Envelope<UserResponse> verifyRegistration(String code) {
        VerifyUserRegistrationCommand command = mapper.toVerifyUserRegistrationCommand(code);
        return verifyUserRegistrationService.execute(command);
    }

    @Override
    public Envelope<RecoverPasswordResponse> recoverPassword(RecoverPasswordRequest request, RequestContext context) {
        RecoverPasswordCommand command = mapper.toRecoverPasswordCommand(request, context);
        return recoverPasswordUseCase.execute(command);
    }

    @Override
    public Envelope<ResetPasswordResponse> resetPassword(ResetPasswordRequest request, RequestContext context) {
        ResetPasswordCommand command = mapper.toResetPasswordCommand(request, context);
        return resetPasswordUseCase.execute(command);
    }

    @Override
    public LoginResult login(LoginRequest request, RequestContext context) {
        LoginUserCommand command = mapper.toLoginUserCommand(request, context);
        return loginUserService.execute(command);
    }

    @Override
    public LoginResult refreshAccessToken(RefreshAccessTokenRequest request, RequestContext context) {
        RefreshAccessTokenCommand command = mapper.toRefreshAccessTokenCommand(request);
        return null; // Disabled temporarily.
    }

    @Override
    public LoginResult refreshAccessToken(RequestContext context) {
        return refreshAccessTokenService.execute(context);
    }

    @Override
    public void logout(RequestContext context) {
        LogoutUserCommand command = mapper.toLogoutUserCommand(context);
        logoutUserUseCase.execute(command);
    }

    @Override
    public Envelope<ChangePasswordResponse> changePassword(User user, ChangePasswordRequest request) {
        ChangePasswordCommand command = mapper.toChangePasswordCommand(request, user.id().value());
        return changePasswordService.execute(command);
    }

    @Override
    public Envelope<ChangeUsernameResponse> changeUsername(User user, ChangeUsernameRequest request) {
        ChangeUserUsernameCommand command = mapper.toChangeUserUsernameCommand(request, user.id().value());
        return changeUserUsernameService.execute(command);
    }

    @Override
    public Envelope<ChangeUsernameResponse> changeExternalUserUsername(User user, ChangeExternalUserUsernameRequest request) {
        ChangeExternalUserUsernameCommand command = mapper.toChangeExternalUserUsernameCommand(
                request,
                user.id().value());
        return changeExternalUserUsernameService.execute(command);
    }

    @Override
    public Envelope<ChangeEmailResponse> changeEmail(User user, ChangeEmailRequest request) {
        ChangeUserEmailCommand command = mapper.toChangeUserEmailCommand(request, user.id().value());
        return changeUserEmailService.execute(command);
    }

    @Override
    public LoginResult OAuthGoogleFlow(OAuthGoogleRequest request, RequestContext context) {
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
    public Envelope<UserResponse> getUserInfo(UserId userId) {
        GetUserInfoCommand command = mapper.toGetUserInfoCommand(userId.value());
        return getUserInfoUseCase.execute(command);
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
