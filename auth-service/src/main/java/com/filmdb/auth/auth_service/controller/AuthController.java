package com.filmdb.auth.auth_service.controller;

import com.filmdb.auth.auth_service.dto.*;
import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.exceptions.InvalidCredentialsException;
import com.filmdb.auth.auth_service.security.CustomUserDetails;
import com.filmdb.auth.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new InvalidCredentialsException("Unauthorized.");
        }
        return customUserDetails.getUser();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        User newUser = authService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.isAdmin()
        );
        UserResponse response = UserResponse.fromUser(newUser);
        return new ResponseEntity<UserResponse>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginUser(
                request.getEmail(),
                request.getPassword());
        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        UserResponse response = UserResponse.fromUser(user);
        return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse> changeUserPassword(@Valid @RequestBody ChangePasswordRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserPassword(user, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Password changed.")
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/me/username")
    public ResponseEntity<ApiResponse> changeUserUsername(@Valid @RequestBody ChangeUsernameRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserUsername(user, request.getCurrentPassword(), request.getUsername());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Username changed.")
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/me/email")
    public ResponseEntity<ApiResponse> changeUserEmail(@Valid @RequestBody ChangeEmailRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserEmail(user, request.getCurrentPassword(), request.getEmail());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Email changed.")
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse> deleteUser(@Valid @RequestBody DeleteUserRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.deleteUser(user, request.getCurrentPassword());
        return ResponseEntity.noContent().build();
    }

}
