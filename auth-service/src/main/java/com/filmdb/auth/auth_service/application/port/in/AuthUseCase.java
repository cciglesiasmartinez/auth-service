package com.filmdb.auth.auth_service.application.port.in;

import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.*;
import org.apache.coyote.Request;


public interface AuthUseCase {

    Envelope<RegisterResponse> register(RegisterRequest request);
    Envelope<UserResponse> verifyRegistration(String code);
    Envelope<?> recoverPassword(RecoverPasswordRequest request, RequestContext context);
    Envelope<LoginResponse> login(LoginRequest request, RequestContext context);
    Envelope<RefreshAccessTokenResponse> refreshAccessToken(RefreshAccessTokenRequest request);
    Envelope<ChangePasswordResponse> changePassword(User user, ChangePasswordRequest request);
    Envelope<ChangeUsernameResponse> changeUsername(User user, ChangeUsernameRequest request);
    Envelope<ChangeUsernameResponse> changeExternalUserUsername(User user, ChangeExternalUserUsernameRequest request);
    Envelope<ChangeEmailResponse> changeEmail(User user, ChangeEmailRequest request);
    Envelope<LoginResponse> OAuthGoogleFlow(OAuthGoogleRequest request, RequestContext context);
    Envelope<UserResponse> getUserInfo(UserId userId);
    void deleteUser(User user, DeleteUserRequest request);
    void deleteExternalUser(User user);

}
