package io.github.cciglesiasmartinez.auth_service.domain.model.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class RefreshTokenString {

    private final String value;

    @JsonCreator
    private RefreshTokenString(@JsonProperty("value") String value) {
        // TODO: Add validations
        this.value = value;
    }

    public static RefreshTokenString of(String value) {
        return new RefreshTokenString(value);
    }

    public String value() {
        return value;
    }

    public String getValue() {
        return value;
    }

}
