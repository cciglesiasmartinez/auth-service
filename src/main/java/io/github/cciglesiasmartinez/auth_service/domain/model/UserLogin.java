package io.github.cciglesiasmartinez.auth_service.domain.model;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.ProviderName;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.ProviderKey;

import java.time.LocalDateTime;

public class
UserLogin {

    private Long id;
    private UserId userId;
    private ProviderKey providerKey;
    private ProviderName providerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserLogin(Long id, UserId userId, ProviderKey providerKey, ProviderName providerName,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        // This constructor is going to be used when recovering a UserLogin from persistence
        this.id = id;
        this.userId = userId;
        this.providerKey = providerKey;
        this.providerName = providerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private UserLogin (UserId userId, ProviderKey providerKey, ProviderName providerName,
                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        // This constructor is going to be used when creating an instance from a factory method
        this.userId = userId;
        this.providerKey = providerKey;
        this.providerName = providerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserLogin create(UserId userId, ProviderKey providerKey, ProviderName providerName) {
        // Factory method that uses the limited constructor when creating a new UserLogin entity
        return new UserLogin(userId, providerKey, providerName, LocalDateTime.now(), LocalDateTime.now());
    }

    public static UserLogin of(Long id, UserId userId, ProviderKey providerKey, ProviderName providerName,
                               LocalDateTime createdAt, LocalDateTime updatedAt) {
        // Factory method that uses the full args constructor to recover an existing UserLogin from persistence
        return new UserLogin(id, userId, providerKey, providerName, createdAt, updatedAt);
    }

    public Long id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public ProviderKey providerKey() {
        return providerKey;
    }

    public ProviderName providerName() {
        return providerName;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updateAt() {
        return updatedAt;
    }

}
