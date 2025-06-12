package com.filmdb.auth.auth_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Username {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    private final String value;

    private Username(String value) {
        this.value = value;
    }

    public static Username of(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        String trimmedUsername = username.trim();
        if (trimmedUsername.length() < MIN_LENGTH || trimmedUsername.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Username must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH)
            );
        }
        return new Username(trimmedUsername);
    }

    public String value() {
        return value;
    }

}
