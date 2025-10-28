package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.exception;

import io.github.cciglesiasmartinez.auth_service.domain.exception.*;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ExceptionResponse;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Envelope<ExceptionResponse>> buildResponse(Exception e, String details) {
        StackTraceElement origin = e.getStackTrace()[0];
        MDC.put("exceptionType", e.getClass().getSimpleName());
        MDC.put("exceptionOrigin", origin.getClassName() + ":" + origin.getLineNumber());
        log.warn(e.getMessage());
        MDC.remove("exceptionOrigin");
        MDC.remove("exceptionType");
        ErrorCatalog catalog = ErrorCatalog.fromException(e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                catalog.getType(),
                catalog.getStatus().value(),
                catalog.getTitle(),
                details,
                catalog.getCode()
        );
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, catalog.getStatus());
    }

    private ResponseEntity<Envelope<ExceptionResponse>> buildResponse(Exception e) {
        StackTraceElement origin = e.getStackTrace()[0];
        MDC.put("exceptionType", e.getClass().getSimpleName());
        MDC.put("exceptionOrigin", origin.getClassName() + ":" + origin.getLineNumber());
        log.warn(e.getMessage());
        MDC.remove("exceptionOrigin");
        MDC.remove("exceptionType");
        ErrorCatalog catalog = ErrorCatalog.fromException(e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                catalog.getType(),
                catalog.getStatus().value(),
                catalog.getTitle(),
                null,
                catalog.getCode()
        );
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, catalog.getStatus());
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e ) {
        return buildResponse(e, e.getMessage());
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<?> handleInvalidUsernameException(InvalidUsernameException e ) {
        return buildResponse(e);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(UserIsExternalException.class)
    public ResponseEntity<?> handleUserIsExternalException(UserIsExternalException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(UserIsNotExternalException.class)
    public ResponseEntity<?> handleUserIsNotExternalException(UserIsNotExternalException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(VerificationCodeNotFoundException.class)
    public ResponseEntity<?> handleVerificationCodeNotFoundException(VerificationCodeNotFoundException e) {
        return buildResponse(e);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<?> handleRefreshTokenExpiredException(RefreshTokenExpiredException e ) {
        return buildResponse(e);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<?> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        return buildResponse(e);
    }

}
