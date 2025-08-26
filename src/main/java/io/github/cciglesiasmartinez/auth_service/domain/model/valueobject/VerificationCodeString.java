package io.github.cciglesiasmartinez.auth_service.domain.model.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class VerificationCodeString {

    private final String value;

    @JsonCreator
    private VerificationCodeString(@JsonProperty("value") String value) {
        // TODO: Add validations
        this.value = value;
    }

    public static VerificationCodeString of(String value) {
        return new VerificationCodeString(value);
    }

    public String value() { return value; }

    public String getValue() { return value; }

}
