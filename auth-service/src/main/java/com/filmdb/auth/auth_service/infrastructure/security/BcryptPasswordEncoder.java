package com.filmdb.auth.auth_service.infrastructure.security;

import com.filmdb.auth.auth_service.domain.model.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.PlainPassword;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public EncodedPassword encode(PlainPassword plainPassword) {
        return EncodedPassword.of(encoder.encode(plainPassword.value()));
    }

    @Override
    public boolean matches(PlainPassword plainPassword, EncodedPassword encodedPassword) {
        return encoder.matches(plainPassword.value(), encodedPassword.value());
    }
}