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
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = authUseCase.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
    public RedirectView redirectToGoogle() {
        String uri = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=568242201835-bvt8okdpe6s364f0b57ea75mqod8ge8t.apps.googleusercontent.com" +
                "&redirect_uri=https://6f07-5-159-173-156.ngrok-free.app/auth/oauth/google/callback" +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&access_type=offline" +
                "&prompt=consent";
        return new RedirectView(uri);
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<LoginResponse> handleGoogleCallback(@RequestParam("code") String code,
                                                       HttpServletRequest httpRequest) {
        System.out.println("[OAUTH] Google code received: " + code);

        // Get data from Google :)
        WebClient webClient = WebClient.create();
        OAuthGoogleRequest tokenResponse = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("code", code)
                        .with("client_id", "568242201835-bvt8okdpe6s364f0b57ea75mqod8ge8t.apps.googleusercontent.com")
                        .with("client_secret", "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")
                        .with("redirect_uri", "https://6f07-5-159-173-156.ngrok-free.app/auth/oauth/google/callback")
                        .with("grant_type", "authorization_code"))
                .retrieve()
                .bodyToMono(OAuthGoogleRequest.class)
                .block();

        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        RequestContext context = new RequestContext(ip, userAgent);
        LoginResponse response = authUseCase.OAuthGoogleFlow(tokenResponse, context);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
