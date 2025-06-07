package com.filmdb.auth.auth_service.controller;

import com.filmdb.auth.auth_service.dto.ChangePasswordRequest;
import com.filmdb.auth.auth_service.dto.LoginRequest;
import com.filmdb.auth.auth_service.dto.LoginResponse;
import com.filmdb.auth.auth_service.dto.RegisterRequest;
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

}
