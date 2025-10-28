package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.exception;

import io.github.cciglesiasmartinez.auth_service.domain.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ErrorCatalog {

    USER_ALREADY_REGISTERED(
            UserAlreadyRegisteredException.class,
            HttpStatus.CONFLICT,
            "https://api.example.com/errors/user_already_registered",
            "User is already registered.",
            "user_already_registered"
    ),

    INVALID_CREDENTIALS(
            InvalidCredentialsException.class,
            HttpStatus.UNAUTHORIZED,
            "https://api.example.com/errors/invalid_credentials",
            "Invalid credentials.",
            "invalid_credentials"
    ),

    PASSWORD_MISMATCH(
            PasswordMismatchException.class,
            HttpStatus.BAD_REQUEST,
            "https://api.example.com/errors/password_mismatch",
            "Password do not match.",
            "password_mismatch"
    ),

    EMAIL_ALREADY_EXISTS(
            EmailAlreadyExistsException.class,
            HttpStatus.CONFLICT,
            "https://api.example.com/errors/email_already_exists",
            "Email already exists.",
            "email_already_exists"
    ),

    USERNAME_ALREADY_EXISTS(
            UsernameAlreadyExistsException.class,
            HttpStatus.CONFLICT,
            "https://api.example.com/errors/username_already_exists",
            "Username already exists.",
            "username_already_exists"
    ),

    INVALID_PASSWORD(
            InvalidPasswordException.class,
            HttpStatus.BAD_REQUEST,
            "https://api.example.com/errors/invalid_password",
            "Invalid password",
            "invalid_password"
    ),

    INVALID_USERNAME(
            InvalidUsernameException.class,
            HttpStatus.BAD_REQUEST,
            "https://api.example.com/errors/invalid_username",
            "Invalid username",
            "invalid_username"
    ),

    USER_NOT_FOUND(
            UserNotFoundException.class,
            HttpStatus.UNAUTHORIZED,
            "https://api.example.com/errors/user_not_found",
            "User not found",
            "user_not_found"
    ),

    USER_IS_EXTERNAL(
            UserIsExternalException.class,
            HttpStatus.UNAUTHORIZED,
            "https://api.example.com/errors/user_is_external",
            "User is external.",
            "user_is_external"
    ),

    USER_IS_NOT_EXTERNAL(
            UserIsNotExternalException.class,
            HttpStatus.UNAUTHORIZED,
            "https://api.example.com/errors/user_is_not_external",
            "User is not external",
            "user_is_not_external"
    ),

    VERIFICATION_CODE_NOT_FOUND(
            VerificationCodeNotFoundException.class,
            HttpStatus.UNAUTHORIZED,
            "https://api.example.com/errors/verification_code_not_found",
            "Verification code not found",
            "verification_code_not_found"
    ),

    REFRESH_TOKEN_EXPIRED(
            RefreshTokenExpiredException.class,
            HttpStatus.BAD_REQUEST,
            "https://api.example.com/errors/refresh_token_expired",
            "Refresh token expired",
            "refresh_token_expired"
    ),

    REFRESH_TOKEN_NOT_FOUND(
            RefreshTokenNotFoundException.class,
            HttpStatus.BAD_REQUEST,
            "https://api.example.com/errors/refresh_token_not_found",
            "Refresh token not found",
            "refresh_token_not_found"
    ),

    INTERNAL_ERROR(
            Exception.class,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "https://api.example.com/errors/internal_error",
            "Internal Server Error",
            "internal_error"
    );

    private final Class<? extends Exception> exceptionClass;
    private final HttpStatus status;
    private final String type;
    private final String title;
    private final String code;

    public static ErrorCatalog fromException(Exception e) {
        return Arrays.stream(values())
                .filter(entry -> entry.exceptionClass.isAssignableFrom(e.getClass())) // Interesting method
                .findFirst()
                .orElse(INTERNAL_ERROR);
    }

}
