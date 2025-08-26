package io.github.cciglesiasmartinez.auth_service.infrastructure.security;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
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