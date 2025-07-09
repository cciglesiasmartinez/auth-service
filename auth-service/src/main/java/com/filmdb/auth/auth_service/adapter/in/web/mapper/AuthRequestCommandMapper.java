package com.filmdb.auth.auth_service.adapter.in.web.mapper;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.application.commands.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;

//@Mapper(componentModel = "spring")
public interface AuthRequestCommandMapper {

    RegisterUserCommand toRegisterUserCommand(RegisterRequest request);
    VerifyUserRegistrationCommand toVerifyUserRegistrationCommand(String code);
    LoginUserCommand toLoginUserCommand(LoginRequest request, RequestContext context);
    ChangePasswordCommand toChangePasswordCommand(ChangePasswordRequest request, String userId);
    ChangeUserEmailCommand toChangeUserEmailCommand(ChangeEmailRequest request, String userId);
    ChangeUserUsernameCommand toChangeUserUsernameCommand(ChangeUsernameRequest request, String userId);
    ChangeExternalUserUsernameCommand toChangeExternalUserUsernameCommand(ChangeExternalUserUsernameRequest request,
                                                                          String userId);
    DeleteUserCommand toDeleteUserCommand(DeleteUserRequest request, String userId);
    DeleteExternalUserCommand toDeleteExternalUserCommand(String userId);
    RefreshAccessTokenCommand toRefreshAccessTokenCommand(RefreshAccessTokenRequest request);
    OAuthGoogleLoginUserCommand toOAuthGoogleLoginUserCommand(String userGoogleId, String userGoogleEmail,
                                                              RequestContext context);
    OAuthGoogleRegisterUserCommand toOAuthGoogleRegisterUserCommand(String userGoogleId, String userGoogleEmail,
                                                                    RequestContext context);

}
