package com.filmdb.auth.auth_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.filmdb.auth.auth_service.application.services.VerificationCodeService;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class VerificationCode {

    private VerificationCodeString verificationCodeString;
    private Username username;
    private Email email;
    private EncodedPassword password;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    @JsonCreator
    private VerificationCode(
            @JsonProperty("code") VerificationCodeString verificationCodeString,
            @JsonProperty("username") Username username,
            @JsonProperty("email") Email email,
            @JsonProperty("password") EncodedPassword password,
            @JsonProperty("issued_at") LocalDateTime issuedAt,
            @JsonProperty("expires_at") LocalDateTime expiresAt
            ) {
        this.verificationCodeString = verificationCodeString;
        this.username = username;
        this.email = email;
        this.password = password;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    // Factory method
    public static VerificationCode create(VerificationCodeString verificationCodeString, Username username,
                                          Email email, EncodedPassword password, LocalDateTime issuedAt,
                                          LocalDateTime expiresAt) {
        return new VerificationCode(verificationCodeString, username, email, password, issuedAt, expiresAt);
    }

    public VerificationCodeString verificationCodeString() {
        return verificationCodeString;
    }

    public Username username() {
        return username;
    }

    public Email email() {
        return email;
    }

    public EncodedPassword password() {
        return password;
    }

    public LocalDateTime issuedAt() {
        return issuedAt;
    }

    public LocalDateTime expiresAt() {
        return expiresAt;
    }

}
