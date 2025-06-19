package com.filmdb.auth.auth_service.application.usecase;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.domain.model.User;


public interface AuthUseCase {

    UserResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request, RequestContext context);
    RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request);
    ChangePasswordResponse changePassword(User user, ChangePasswordRequest request);
    ChangeUsernameResponse changeUsername(User user, ChangeUsernameRequest request);
    ChangeEmailResponse changeEmail(User user, ChangeEmailRequest request);
    void deleteUser(User user, DeleteUserRequest request);

}
