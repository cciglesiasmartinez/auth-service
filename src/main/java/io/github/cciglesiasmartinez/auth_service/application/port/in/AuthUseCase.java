package io.github.cciglesiasmartinez.auth_service.application.port.in;

import io.github.cciglesiasmartinez.auth_service.application.dto.LoginResult;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests.*;
import io.github.cciglesiasmartinez.auth_service.application.context.RequestContext;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.*;


public interface AuthUseCase {

    Envelope<RegisterResponse> register(RegisterRequest request);
    Envelope<UserResponse> verifyRegistration(String code);
    Envelope<RecoverPasswordResponse> recoverPassword(RecoverPasswordRequest request, RequestContext context);
    Envelope<ResetPasswordResponse> resetPassword(ResetPasswordRequest request, RequestContext context);
    LoginResult login(LoginRequest request, RequestContext context);
    LoginResult refreshAccessToken(RefreshAccessTokenRequest request, RequestContext context);
    LoginResult refreshAccessToken(RequestContext context);
    Envelope<ChangePasswordResponse> changePassword(User user, ChangePasswordRequest request);
    Envelope<ChangeUsernameResponse> changeUsername(User user, ChangeUsernameRequest request);
    Envelope<ChangeUsernameResponse> changeExternalUserUsername(User user, ChangeExternalUserUsernameRequest request);
    Envelope<ChangeEmailResponse> changeEmail(User user, ChangeEmailRequest request);
    LoginResult OAuthGoogleFlow(OAuthGoogleRequest request, RequestContext context);
    Envelope<UserResponse> getUserInfo(UserId userId);
    void deleteUser(User user, DeleteUserRequest request);
    void deleteExternalUser(User user);

}
