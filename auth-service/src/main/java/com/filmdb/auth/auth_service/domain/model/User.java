package com.filmdb.auth.auth_service.domain.model;

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
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;

    private User(UserId id, Username username, EncodedPassword password, Email email, LocalDateTime registeredAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredAt = registeredAt;
    }

    /**
     * Factory method that creates a new {@link User} object from {@link Username }, {@link Email}
     * and {@link PlainPassword} value objects.
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
        return new User(UserId.generate(), username, encodedPassword, email, LocalDateTime.now());
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
                          LocalDateTime registeredAt, LocalDateTime modifiedAt) {
        User user = new User(id, username, password, email, registeredAt);
        user.modifiedAt = modifiedAt;
        return user;
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
     *  Updates the {@code modifiedAt} field with the current timestamp.
     */
    public void markAsModified() {
        this.modifiedAt = LocalDateTime.now();
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

}
