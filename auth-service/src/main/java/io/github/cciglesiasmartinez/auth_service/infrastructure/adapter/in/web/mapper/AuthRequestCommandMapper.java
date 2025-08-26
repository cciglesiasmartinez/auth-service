package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.mapper;

import io.github.cciglesiasmartinez.auth_service.application.usecases.getuserinfo.GetUserInfoCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.recoverpassword.RecoverPasswordCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.resetpassword.ResetPasswordCommand;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests.*;
import io.github.cciglesiasmartinez.auth_service.application.context.RequestContext;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeemail.ChangeUserEmailCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changepassword.ChangePasswordCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername.ChangeExternalUserUsernameCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername.ChangeUserUsernameCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser.DeleteExternalUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser.DeleteUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.login.LoginUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.logingoogle.OAuthGoogleLoginUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken.RefreshAccessTokenCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.register.RegisterUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.registergoogle.OAuthGoogleRegisterUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.verifyregistration.VerifyUserRegistrationCommand;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests.*;

//@Mapper(componentModel = "spring")
public interface AuthRequestCommandMapper {

    RegisterUserCommand toRegisterUserCommand(RegisterRequest request);
    VerifyUserRegistrationCommand toVerifyUserRegistrationCommand(String code);
    RecoverPasswordCommand toRecoverPasswordCommand(RecoverPasswordRequest request, RequestContext context);
    ResetPasswordCommand toResetPasswordCommand(ResetPasswordRequest request, RequestContext context);
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
