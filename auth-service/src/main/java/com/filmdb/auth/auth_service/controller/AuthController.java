package com.filmdb.auth.auth_service.controller;

import com.filmdb.auth.auth_service.dto.*;
import com.filmdb.auth.auth_service.entity.User;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        try {
            User newUser = authService.registerUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.isAdmin()
            );
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = authService.loginUser(
                    request.getEmail(),
                    request.getPassword()
            );
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        System.out.println("OK, request to /me received, auth is: " + authentication);
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changeUserPassword(@RequestBody ChangePasswordRequest request,
                                                Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        try {
            authService.changeUserPassword(user, request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.ok("Password changed.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/me/username")
    public ResponseEntity<?> changeUserUsername(@Valid @RequestBody ChangeUsernameRequest request,
                                                Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        User user = customUserDetails.getUser();
        try {
            authService.changeUserUsername(user, request.getCurrentPassword(), request.getUsername());
            return ResponseEntity.ok("Username changed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/me/email")
    public ResponseEntity<?> changeUserEmail(@Valid @RequestBody ChangeEmailRequest request,
                                                Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        User user = customUserDetails.getUser();
        try {
            authService.changeUserUsername(user, request.getCurrentPassword(), request.getEmail());
            return ResponseEntity.ok("Email changed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request,
                                                Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        User user = customUserDetails.getUser();
        try {
            authService.deleteUser(user, request.getCurrentPassword());
            return ResponseEntity.ok("User deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }


}
