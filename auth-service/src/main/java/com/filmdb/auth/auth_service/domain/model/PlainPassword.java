package com.filmdb.auth.auth_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class PlainPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 50;

    private final String value;

    private PlainPassword(String value) {
        this.value = value;
    }

    public static PlainPassword of(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Password must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH)
            );
        }
        return new PlainPassword(password);
    }

    public String value() {
        return value;
    }

}
