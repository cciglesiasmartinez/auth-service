package com.filmdb.auth.auth_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecoverCode {

    private RecoverCodeString recoverCodeString;
    private Email email;
    private String ip;
    private String userAgent;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    @JsonCreator
    private RecoverCode(
            @JsonProperty("code") RecoverCodeString recoverCodeString,
            @JsonProperty("email") Email email,
            @JsonProperty("ip") String ip,
            @JsonProperty("user_agent") String userAgent,
            @JsonProperty("issued_at") LocalDateTime issuedAt,
            @JsonProperty("expires_at") LocalDateTime expiresAt
    ) {
        this.recoverCodeString = recoverCodeString;
        this.email = email;
        this.ip = ip;
        this.userAgent = userAgent;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    // Factory method
    public static RecoverCode create(RecoverCodeString recoverCodeString, Email email, String ip, String userAgent,
                                     LocalDateTime issuedAt, LocalDateTime expiresAt) {
        return new RecoverCode(recoverCodeString, email, ip, userAgent, issuedAt, expiresAt);
    }

    public RecoverCodeString recoverCodeString() {
        return recoverCodeString;
    }

    public Email email() {
        return email;
    }

    public String ip() {
        return ip;
    }

    public String userAgent() {
        return userAgent;
    }

    public LocalDateTime issuedAt() {
        return issuedAt;
    }

    public LocalDateTime expiresAt() {
        return expiresAt;
    }

}
