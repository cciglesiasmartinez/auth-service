package com.filmdb.auth.auth_service.domain.model.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class RecoverCodeString {

    private final String value;

    @JsonCreator
    private RecoverCodeString(@JsonProperty("value") String value) {
        // TODO: Add validations
        this.value = value;
    }

    public static RecoverCodeString of(String value) {
        return new RecoverCodeString(value);
    }

    public String value() { return value; }

    public String getValue() { return value; }

}
