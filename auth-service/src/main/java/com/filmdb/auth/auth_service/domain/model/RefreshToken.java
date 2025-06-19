package com.filmdb.auth.auth_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString // Delete in production
public class RefreshToken {

    private RefreshTokenString token;
    private UserId userId;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private String ipAddress;
    private String userAgent;

    @JsonCreator
    private RefreshToken(
            @JsonProperty("token") RefreshTokenString token,
            @JsonProperty("userId") UserId userId,
            @JsonProperty("issuedAt") LocalDateTime issuedAt,
            @JsonProperty("expiresAt") LocalDateTime expiresAt,
            @JsonProperty("ipAddress") String ipAddress,
            @JsonProperty("userAgent") String userAgent) {
        // TODO: Add validations.
        this.token = token;
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    // Getters
    public RefreshTokenString token() {
        return token;
    }

    public UserId userId() {
        return userId;
    }

    public LocalDateTime issuedAt() {
        return issuedAt;
    }

    public LocalDateTime expiresAt() {
        return expiresAt;
    }

    public String ipAddress() {
        return ipAddress;
    }

    public String userAgent() {
        return userAgent;
    }

    // Factory method
    public static RefreshToken create(RefreshTokenString token, UserId userId, LocalDateTime issuedAt,
                                      LocalDateTime expiresAt, String ipAddress, String userAgent) {
        return new RefreshToken(token, userId, issuedAt, expiresAt, ipAddress, userAgent);
    }

}
