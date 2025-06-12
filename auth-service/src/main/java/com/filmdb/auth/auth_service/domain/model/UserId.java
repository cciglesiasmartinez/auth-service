package com.filmdb.auth.auth_service.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
public final class UserId {

    private final String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
    }

    public static UserId of(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        try {
            UUID.fromString(id.trim());
        } catch (IllegalArgumentException e ) {
            throw new IllegalArgumentException("Invalid UUID format for User ID: " + id);
        }
        return new UserId(id.trim());
    }

    public String value() {
        return value;
    }

}
