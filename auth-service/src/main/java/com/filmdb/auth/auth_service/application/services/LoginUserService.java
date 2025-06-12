package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.LoginUserCommand;
import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import com.filmdb.auth.auth_service.dto.responses.LoginResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginResponse execute(LoginUserCommand command) {
        Email email = Email.of(command.email());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials."));
        PlainPassword plainPassword = PlainPassword.of(command.password());
        if (!passwordEncoder.matches(plainPassword, user.password())) {
            throw new RuntimeException("Invalid credentials.");
        }
        String token = tokenProvider.generateToken(user.username().toString());
        long expiresIn = tokenProvider.getTokenExpirationInSeconds();
        // Change username type from String to Username before completing migration
        return new LoginResponse(token, expiresIn, user.username().toString());
    }

}
