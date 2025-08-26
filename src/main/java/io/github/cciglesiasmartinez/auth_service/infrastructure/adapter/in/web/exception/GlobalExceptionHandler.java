package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.exception;

import io.github.cciglesiasmartinez.auth_service.domain.exception.*;
import io.github.cciglesiasmartinez.auth_service.domain.exception.*;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ExceptionResponse;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Envelope<ExceptionResponse>> buildResponse(String code, HttpStatus type) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(code);
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, type);
    }

    private ResponseEntity<Envelope<ExceptionResponse>> buildResponse(String code, String message, HttpStatus type) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(code, message);
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, type);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        return buildResponse("user_already_registered", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return buildResponse("invalid_credentials", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException e) {
        return buildResponse("password_mismatch", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return buildResponse("email_already_exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return buildResponse("username_already_exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e ) {
        return buildResponse("invalid_password", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<?> handleInvalidUsernameException(InvalidUsernameException e ) {
        return buildResponse("invalid_username", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponse("user_not_found", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsExternalException.class)
    public ResponseEntity<?> handleUserIsExternalException(UserIsExternalException e) {
        return buildResponse("unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsNotExternalException.class)
    public ResponseEntity<?> handleUserIsNotExternalException(UserIsNotExternalException e) {
        return buildResponse("user_is_not_external", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(VerificationCodeNotFoundException.class)
    public ResponseEntity<?> handleVerificationCodeNotFoundException(VerificationCodeNotFoundException e) {
        return buildResponse("verification_code_not_found", HttpStatus.UNAUTHORIZED);
    }

}
