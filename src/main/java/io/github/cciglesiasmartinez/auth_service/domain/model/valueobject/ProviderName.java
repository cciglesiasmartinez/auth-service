package io.github.cciglesiasmartinez.auth_service.domain.model.valueobject;

// TODO: Document this thoroughly.
public enum ProviderName {
    GOOGLE("Google");

    private final String value;

    ProviderName(String providerName) {
        this.value = providerName;
    }

    public static ProviderName of(String providerName) {
        // TODO: Create custom domain exceptions
        if ((providerName == null) || providerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider name can't be null or empty");
        }
        for (ProviderName name : values()) {
            if ( name.value.equalsIgnoreCase(providerName.trim())) {
                return name;
            }
        }
        throw new IllegalArgumentException("Unknown provider name: " + providerName);
    }

    public String value() {
        return value;
    }

}
