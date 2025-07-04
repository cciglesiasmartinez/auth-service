package com.filmdb.auth.auth_service.domain.model;

import com.filmdb.auth.auth_service.application.exception.InvalidCredentialsException;
import com.filmdb.auth.auth_service.application.usecases.OAuthGoogleLoginUserUseCase;
import com.filmdb.auth.auth_service.domain.model.valueobject.*;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import com.filmdb.auth.auth_service.infrastructure.persistence.mysql.entity.UserEntity;

import java.time.LocalDateTime;

/**
 * Domain model representing a User within the system.
 * <p>
 * This class encapsulates the business logic and state for a registered user, including identity, authentication
 * credentials, and audit timestamps.
 * <p>
 * Instances of this class are either created during user registration or reconstructed from persistent storage
 * via mappers.
 */
public class User {

    private final UserId id;
    private Username username;
    private EncodedPassword password;
    private Email email;
    private final LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime lastLogin;
    private boolean isExternal;

    private User(UserId id, Username username, EncodedPassword password, Email email, LocalDateTime registeredAt,
                 LocalDateTime modifiedAt, LocalDateTime lastLogin, boolean isExternal) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredAt = registeredAt;
        this.modifiedAt = modifiedAt;
        this.lastLogin = lastLogin;
        this.isExternal = isExternal;
    }

    /**
     * Factory method that creates a new {@link User} object from {@link Username }, {@link Email}
     * and {@link PlainPassword} value objects for internally authenticated users.
     *
     * @param username Username for the new user.
     * @param email Email for the new user.
     * @param plainPassword Password in plaintext for the new user.
     * @param passwordEncoder Encoder needed to encrypt the plaintext password.
     * @return a new {@link User} instance.
     */
    public static User create(Username username, Email email, PlainPassword plainPassword,
                              PasswordEncoder passwordEncoder) {
        EncodedPassword encodedPassword = passwordEncoder.encode(plainPassword);
        return new User(UserId.generate(), username, encodedPassword, email, LocalDateTime.now(), LocalDateTime.now(),
                null, false);
    }

    /**
     * Factory method that creates a new {@link User} object for externally authenticated (ie: OAuth) users.
     * <p>
     * These users contain a {@code null} value {@link EncodedPassword} and a procedurally generated
     * default {@link Username}.
     *
     * @param email Email from external provider.
     * @param providerKey ID from external provider.
     * @param providerName name of the external provider.
     * @return a new {@link User} instance.
     */
    public static User createExternalUser(Email email, ProviderKey providerKey, ProviderName providerName) {
        Username username = Username.createDefaultExternalUsername(providerName, providerKey);
        EncodedPassword externalNullPassword = EncodedPassword.externalNullPassword();
        return new User(UserId.generate(), username, externalNullPassword, email, LocalDateTime.now(),
                LocalDateTime.now(), null, true);
    }

    /**
     * Factory method used to reconstruct a {@link User} domain object from persistent data.
     * <p>
     * Intended to be used by mappers when converting from {@link UserEntity} to {@link User}.
     *
     * @param id User's unique identifier.
     * @param username User's username.
     * @param password Encoded password.
     * @param email User's email address.
     * @param registeredAt Timestamp when the user was registered.
     * @param modifiedAt Timestamp of the last modification.
     * @return a fully constructed {@link User} domain object
     */
    public static User of(UserId id, Username username, EncodedPassword password, Email email,
                          LocalDateTime registeredAt, LocalDateTime modifiedAt, LocalDateTime lastLogin,
                          boolean isExternal) {
        return new User(id, username, password, email, registeredAt, modifiedAt, lastLogin, isExternal);
    }

    /**
     * Changes the password of the user after validating the current (provided) password.
     *
     * @param currentPassword the raw password to validate.
     * @param newPassword the new password for the user.
     * @param passwordEncoder the encoder used to perform the password comparison.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     */
    public void changePassword(PlainPassword currentPassword, PlainPassword newPassword,
                               PasswordEncoder passwordEncoder) {
        validateCurrentPassword(currentPassword, passwordEncoder);
        this.password = passwordEncoder.encode(newPassword);
        markAsModified();
    }

    /**
     * Changes the username of the user after validating the provided password.
     *
     * @param currentPassword the raw password to validate.
     * @param newUsername the new username for the user.
     * @param passwordEncoder the encoder used to perform the password comparison.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     */
    public void changeUsername(PlainPassword currentPassword, Username newUsername,
                               PasswordEncoder passwordEncoder) {
        validateCurrentPassword(currentPassword, passwordEncoder);
        this.username = newUsername;
        markAsModified();
    }

    /**
     * Changes the email of the user after validating the provided password.
     *
     * @param currentPassword the raw password to validate.
     * @param newEmail the new email for the user.
     * @param passwordEncoder the encoder used to perform the password comparison.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     */
    public void changeEmail(PlainPassword currentPassword, Email newEmail,
                            PasswordEncoder passwordEncoder) {
        validateCurrentPassword(currentPassword, passwordEncoder);
        this.email = newEmail;
        markAsModified();
    }

    /**
     * Validates whether the provided raw password matches the stored encoded password.
     * <p>
     * This method delegates password matching logic to the provided {@link PasswordEncoder}.
     *
     * @param currentPassword the raw password to validate.
     * @param passwordEncoder the encoder used to perform the password comparison.
     * @throws PasswordMismatchException if the provided password does not match the stored password.
     */
    public void validateCurrentPassword(PlainPassword currentPassword,
                                        PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(currentPassword, this.password)) {
            throw new PasswordMismatchException();
        }
    }

    /**
     * Validates the provided password during the login flow. This version throws a generic
     * {@link InvalidCredentialsException} to avoid revealing whether the user exists or the password is wrong.
     * <p>
     * This prevents user enumeration attacks.
     *
     * @param loginPassword the raw password provided at login.
     * @param passwordEncoder the encoder used to check password match.
     * @throws InvalidCredentialsException if the password does not match.
     */
    public void validateLoginPassword(PlainPassword loginPassword,
                                      PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(loginPassword, this.password)) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
    }

    /**
     *  Updates the {@code modifiedAt} field with the current timestamp.
     */
    public void markAsModified() {
        this.modifiedAt = LocalDateTime.now();
    }

    /**
     * Updates {@code lastLogin} field with current timestamp.
     */
    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    /**
     * Updates {@code email} field for external users. Intended to be used in
     * {@link OAuthGoogleLoginUserUseCase} use case when mail
     * received in user's Google JWT token does not match the current mail for the user persisted in our db.
     *
     * @param newEmail new email for the user.
     */
    public void updateEmailIfDifferent(Email newEmail) {
        if (!this.email.equals(newEmail)) {
            this.email = newEmail;
        }
    }

    /**
     * Updates {@code username} field for external users. It works in a more straightforward way than the standard
     * changeUsername method, since we don't have a password to check against.
     *
     * @param username the new username for the user.
     */
    public void changeUsernameForExternalUser(Username username) {
        this.username = username;
    }

    public UserId id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public EncodedPassword password() {
        return password;
    }

    public Email email() {
        return email;
    }

    public LocalDateTime registeredAt() {
        return registeredAt;
    }

    public LocalDateTime modifiedAt() {
        return modifiedAt;
    }

    public LocalDateTime lastLogin() {
        return lastLogin;
    }

    public boolean isExternal() {
        return isExternal;
    }

}
