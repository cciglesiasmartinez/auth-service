package com.filmdb.auth.auth_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Value Object representing a valid username.
 * <p>
 * A username must be a non-null, non-empty string with a length between 3 and 30 characters.
 * Leading and trailing whitespace is trimmed before validation.
 */
@EqualsAndHashCode
@ToString
public final class Username {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    private final String value;

    private Username(String value) {
        this.value = value;
    }

    /**
     * Creates a {@code Username} instance from a raw string.
     *
     * @param username the raw username string
     * @return a validated {@code Username} instance
     * @throws IllegalArgumentException if the username is null, empty, or not within the allowed length
     */
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

    /**
     * Returns the raw string value of the username.
     *
     * @return the username string
     */
    public String value() {
        return value;
    }

}
