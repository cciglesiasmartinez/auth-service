package com.filmdb.auth.auth_service.infrastructure.security;

import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;
import com.filmdb.auth.auth_service.domain.port.out.RecoverCodeGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AlphanumericRecoverCodeGenerator implements RecoverCodeGenerator {

    private final SecureRandom secureRandom = new SecureRandom();
    private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    @Override
    public RecoverCodeString generate() {
        StringBuilder code = new StringBuilder(6);
        for ( int i = 0; i < CODE_LENGTH; i++ ) {
            code.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        return RecoverCodeString.of(code.toString());
    }

}
