package com.filmdb.auth.auth_service.adapter.in.web.exception;

import com.filmdb.auth.auth_service.application.exception.*;
import com.filmdb.auth.auth_service.domain.exception.InvalidPasswordException;
import com.filmdb.auth.auth_service.domain.exception.InvalidUsernameException;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO: Think about defining a universal DTO for errors.

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<?> handleUserAlreadyRegistered(UserAlreadyRegisteredException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<?> handleInvalidUsernameException(InvalidUsernameException e ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    // TODO: Reconsider the body message for this case. Also consider standard DTO's for exceptions.
    @ExceptionHandler(UserIsExternalException.class)
    public ResponseEntity<?> handleUserIsExternalException(UserIsExternalException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

}
