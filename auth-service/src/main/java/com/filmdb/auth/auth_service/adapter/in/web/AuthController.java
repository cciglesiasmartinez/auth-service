package com.filmdb.auth.auth_service.adapter.in.web;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.*;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.*;
import com.filmdb.auth.auth_service.application.context.RequestContext;
import com.filmdb.auth.auth_service.application.usecases.AuthUseCase;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.application.exception.InvalidCredentialsException;
import com.filmdb.auth.auth_service.infrastructure.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails customUserDetails)) {
            throw new InvalidCredentialsException("Unauthorized");
        }
        return customUserDetails.getUser();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authUseCase.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyRegistration(@RequestParam("code") String code) {
        UserResponse response = authUseCase.verifyRegistration(code);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        RequestContext context = new RequestContext(ip, userAgent);
        LoginResponse response = authUseCase.login(request, context);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshAccessTokenResponse> refresh(@Valid @RequestBody RefreshAccessTokenRequest request) {
        RefreshAccessTokenResponse response = authUseCase.refreshAccessToken(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        UserResponse response = UserResponse.fromDomainUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/me/password")
    public ResponseEntity<ChangePasswordResponse> changeUserPassword(@Valid @RequestBody ChangePasswordRequest request,
                                                                     Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authUseCase.changePassword(user, request);
        ChangePasswordResponse response = new ChangePasswordResponse("Password changed", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/me/username")
    public ResponseEntity<ChangeUsernameResponse> changeUserUsername(@Valid @RequestBody ChangeUsernameRequest request,
                                                                     Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        ChangeUsernameResponse response = authUseCase.changeUsername(user, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/me/email")
    public ResponseEntity<ChangeEmailResponse> changeUserEmail(@Valid @RequestBody ChangeEmailRequest request,
                                                               Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        ChangeEmailResponse response = authUseCase.changeEmail(user, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest request,
                                        Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authUseCase.deleteUser(user, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/oauth/google")
    public RedirectView redirectToGoogle(@Value("${google.oauth.client-id}") String clientId,
                                         @Value("${google.oauth.redirect-uri}") String redirectUri) {
        String uri = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&access_type=offline" +
                "&prompt=consent";
        return new RedirectView(uri);
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<LoginResponse> handleGoogleCallback(
            @RequestParam("code") String code,
            HttpServletRequest httpRequest,
            @Value("${google.oauth.client-id}") String clientId,
            @Value("${google.oauth.redirect-uri}") String redirectUri,
            @Value("${google.oauth.client-secret}") String clientSecret) {
        WebClient webClient = WebClient.create();
        OAuthGoogleRequest googleResponse = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("code", code)
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("grant_type", "authorization_code"))
                .retrieve()
                .bodyToMono(OAuthGoogleRequest.class)
                .block();
        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        RequestContext context = new RequestContext(ip, userAgent);
        LoginResponse response = authUseCase.OAuthGoogleFlow(googleResponse, context);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/oauth/me/username")
    public ResponseEntity<ChangeUsernameResponse> changeExternalUserUsername(
            @Valid @RequestBody ChangeExternalUserUsernameRequest request,
            Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        ChangeUsernameResponse response = authUseCase.changeExternalUserUsername(user, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/oauth/me")
    public ResponseEntity<?> deleteExternalUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        authUseCase.deleteExternalUser(user);
        return ResponseEntity.noContent().build();
    }

}
