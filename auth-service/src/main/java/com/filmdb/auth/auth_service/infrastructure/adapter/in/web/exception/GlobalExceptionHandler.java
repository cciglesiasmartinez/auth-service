package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.exception;

import com.filmdb.auth.auth_service.domain.exception.*;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.ExceptionResponse;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("invalid_credentials");
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<?> handleInvalidUsernameException(InvalidUsernameException e ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // TODO: Reconsider the body message for this case. Also consider standard DTO's for exceptions.
    @ExceptionHandler(UserIsExternalException.class)
    public ResponseEntity<?> handleUserIsExternalException(UserIsExternalException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("unauthorized");
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIsNotExternalException.class)
    public ResponseEntity<?> handleUserIsNotExternalException(UserIsNotExternalException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(VerificationCodeNotFoundException.class)
    public ResponseEntity<?> handleVerificationCodeNotFoundException(VerificationCodeNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        Envelope<ExceptionResponse> response = new Envelope<>(exceptionResponse, new Meta());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
