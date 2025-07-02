package com.filmdb.auth.auth_service.domain.model.valueobject;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ProviderKey {

    private final String value;

    private ProviderKey(String value) {
        this.value = value;
    }

    public static ProviderKey of(String providerKey) {
        // TODO: Any logic here?
        return new ProviderKey(providerKey);
    }

    public String value() {
        return value;
    }

}
