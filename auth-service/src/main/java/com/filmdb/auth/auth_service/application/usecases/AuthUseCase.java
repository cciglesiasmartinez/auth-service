package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.domain.model.User;


public interface AuthUseCase {

    RegisterResponse register(RegisterRequest request);
    UserResponse verifyRegistration(String code);
    LoginResponse login(LoginRequest request, RequestContext context);
    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request);
    ChangePasswordResponse changePassword(User user, ChangePasswordRequest request);
    ChangeUsernameResponse changeUsername(User user, ChangeUsernameRequest request);
    ChangeUsernameResponse changeExternalUserUsername(User user, ChangeExternalUserUsernameRequest request);
    ChangeEmailResponse changeEmail(User user, ChangeEmailRequest request);
    LoginResponse OAuthGoogleFlow(OAuthGoogleRequest request, RequestContext context);
    void deleteUser(User user, DeleteUserRequest request);
    void deleteExternalUser(User user);

}
