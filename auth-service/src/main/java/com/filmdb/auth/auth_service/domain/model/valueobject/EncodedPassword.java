package com.filmdb.auth.auth_service.domain.model.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class EncodedPassword {

    private final String value;

    private EncodedPassword(String value) {
        this.value = value;
    }

    public static EncodedPassword of(String encodedPassowrd) {
        // TODO: Solve this in a graceful and consistent manner :)
//        if (encodedPassowrd == null || encodedPassowrd.isEmpty()) {
//            throw new IllegalArgumentException("Password cannot be null or empty");
//        }
        return new EncodedPassword(encodedPassowrd);
    }

    public static EncodedPassword externalNullPassword() {
        return new EncodedPassword(null);
    }

    public String value() {
        return this.value;
    }

}
