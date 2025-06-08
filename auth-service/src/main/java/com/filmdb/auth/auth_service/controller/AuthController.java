package com.filmdb.auth.auth_service.controller;

import com.filmdb.auth.auth_service.dto.*;
import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.exceptions.InvalidCredentialsException;
import com.filmdb.auth.auth_service.exceptions.UserAlreadyRegisteredException;
import com.filmdb.auth.auth_service.security.CustomUserDetails;
import com.filmdb.auth.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        return customUserDetails.getUser();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        User newUser = authService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.isAdmin());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginUser(
                request.getEmail(),
                request.getPassword());
        return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangePasswordRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserPassword(user, request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed.");
    }

    @PutMapping("/me/username")
    public ResponseEntity<?> changeUserUsername(@Valid @RequestBody ChangeUsernameRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserUsername(user, request.getCurrentPassword(), request.getUsername());
        return ResponseEntity.ok("Username changed.");
    }

    @PutMapping("/me/email")
    public ResponseEntity<?> changeUserEmail(@Valid @RequestBody ChangeEmailRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.changeUserEmail(user, request.getCurrentPassword(), request.getEmail());
        return ResponseEntity.ok("Email changed.");
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request,
                                                Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authService.deleteUser(user, request.getCurrentPassword());
        return ResponseEntity.ok("User deleted.");
    }

}
