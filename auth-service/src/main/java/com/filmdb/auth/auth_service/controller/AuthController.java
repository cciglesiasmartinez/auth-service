package com.filmdb.auth.auth_service.controller;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.*;
import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.application.exception.InvalidCredentialsException;
import com.filmdb.auth.auth_service.infrastructure.security.CustomUserDetails;
import com.filmdb.auth.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

//@RestController
//@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new InvalidCredentialsException("Unauthorized.");
        }
        User user = new User();
        user.setUsername(customUserDetails.getUsername());
        user.setPassword(customUserDetails.getPassword());
        return user;
        //return customUserDetails.getUser();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = authService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.loginUser(
                request.getEmail(),
                request.getPassword());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        UserResponse response = UserResponse.fromUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/me/password")
    public ResponseEntity<ChangePasswordResponse> changeUserPassword(@Valid @RequestBody ChangePasswordRequest request,
                                                                     Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserPassword(user, request.getCurrentPassword(), request.getNewPassword());
        ChangePasswordResponse response = new ChangePasswordResponse("Password changed", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/me/username")
    public ResponseEntity<ChangeUsernameResponse> changeUserUsername(@Valid @RequestBody ChangeUsernameRequest request,
                                                                     Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        ChangeUsernameResponse response = authService.changeUserUsername(
                user,
                request.getCurrentPassword(),
                request.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/me/email")
    public ResponseEntity<ChangeEmailResponse> changeUserEmail(@Valid @RequestBody ChangeEmailRequest request,
                                                               Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        ChangeEmailResponse response = authService.changeUserEmail(
                user,
                request.getCurrentPassword(),
                request.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request,
                                                  Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.deleteUser(user, request.getCurrentPassword());
        return ResponseEntity.noContent().build();
    }

}
