package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.exception;

import io.github.cciglesiasmartinez.auth_service.domain.exception.*;
import io.github.cciglesiasmartinez.auth_service.domain.exception.*;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ExceptionResponse;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Envelope<ExceptionResponse>> buildResponse(Exception e, String code, HttpStatus type) {
        StackTraceElement origin = e.getStackTrace()[0];
        MDC.put("exceptionType", e.getClass().getSimpleName());
        MDC.put("exceptionOrigin", origin.getClassName() + ":" + origin.getLineNumber());
        log.warn(e.getMessage());
        MDC.remove("exceptionOrigin");
        MDC.remove("exceptionType");
        ExceptionResponse exceptionResponse = new ExceptionResponse(code);
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, type);
    }

    private ResponseEntity<Envelope<ExceptionResponse>> buildResponse(Exception e, String code, String message,
                                                                      HttpStatus type) {
        StackTraceElement origin = e.getStackTrace()[0];
        MDC.put("exceptionType", e.getClass().getSimpleName());
        MDC.put("exceptionOrigin",origin.getClassName() + ":" + origin.getLineNumber());
        log.warn(e.getMessage());
        MDC.remove("exceptionOrigin");
        MDC.remove("exceptionType");
        ExceptionResponse exceptionResponse = new ExceptionResponse(code, message);
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, type);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        return buildResponse(e, "user_already_registered", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return buildResponse(e, "invalid_credentials", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException e) {
        return buildResponse(e, "password_mismatch", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return buildResponse(e, "email_already_exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return buildResponse(e, "username_already_exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e ) {
        return buildResponse(e, "invalid_password", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<?> handleInvalidUsernameException(InvalidUsernameException e ) {
        return buildResponse(e, "invalid_username", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponse(e, "user_not_found", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsExternalException.class)
    public ResponseEntity<?> handleUserIsExternalException(UserIsExternalException e) {
        return buildResponse(e, "unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsNotExternalException.class)
    public ResponseEntity<?> handleUserIsNotExternalException(UserIsNotExternalException e) {
        return buildResponse(e, "user_is_not_external", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(VerificationCodeNotFoundException.class)
    public ResponseEntity<?> handleVerificationCodeNotFoundException(VerificationCodeNotFoundException e) {
        return buildResponse(e, "verification_code_not_found", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<?> handleRefreshTokenExpiredException(RefreshTokenExpiredException e ) {
        return buildResponse(e, "refresh_token_expired", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<?> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        return buildResponse(e, "refresh_token_not_found", HttpStatus.BAD_REQUEST);
    }

}
