package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.mapper;

import com.filmdb.auth.auth_service.application.usecases.getuserinfo.GetUserInfoCommand;
import com.filmdb.auth.auth_service.application.usecases.recoverpassword.RecoverPasswordCommand;
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

//@Mapper(componentModel = "spring")
public interface AuthRequestCommandMapper {

    RegisterUserCommand toRegisterUserCommand(RegisterRequest request);
    VerifyUserRegistrationCommand toVerifyUserRegistrationCommand(String code);
    RecoverPasswordCommand toRecoverPasswordCommand(RecoverPasswordRequest request, RequestContext context);
    LoginUserCommand toLoginUserCommand(LoginRequest request, RequestContext context);
    ChangePasswordCommand toChangePasswordCommand(ChangePasswordRequest request, String userId);
    ChangeUserEmailCommand toChangeUserEmailCommand(ChangeEmailRequest request, String userId);
    ChangeUserUsernameCommand toChangeUserUsernameCommand(ChangeUsernameRequest request, String userId);
    ChangeExternalUserUsernameCommand toChangeExternalUserUsernameCommand(ChangeExternalUserUsernameRequest request,
                                                                          String userId);
    DeleteUserCommand toDeleteUserCommand(DeleteUserRequest request, String userId);
    DeleteExternalUserCommand toDeleteExternalUserCommand(String userId);
    RefreshAccessTokenCommand toRefreshAccessTokenCommand(RefreshAccessTokenRequest request);
    GetUserInfoCommand toGetUserInfoCommand(String userId);
    OAuthGoogleLoginUserCommand toOAuthGoogleLoginUserCommand(String userGoogleId, String userGoogleEmail,
                                                              RequestContext context);
    OAuthGoogleRegisterUserCommand toOAuthGoogleRegisterUserCommand(String userGoogleId, String userGoogleEmail,
                                                                    RequestContext context);

}
