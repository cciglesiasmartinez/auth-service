package com.filmdb.auth.auth_service.domain.model;

import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.exceptions.PasswordMismatchException;

import java.time.LocalDateTime;

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

    // Factory method
    public static User create(Username username, Email email, PlainPassword plainPassword,
                              PasswordEncoder passwordEncoder) {
        EncodedPassword encodedPassword = passwordEncoder.encode(plainPassword);
        return new User(UserId.generate(), username, encodedPassword, email, LocalDateTime.now());
    }

    // Getters
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

    // Domain methods
    public void changePassword(PlainPassword currentPassword, PlainPassword newPassword,
                               PasswordEncoder passwordEncoder) {
        validateCurrentPassword(currentPassword, passwordEncoder);
        this.password = passwordEncoder.encode(newPassword);
        this.modifiedAt = LocalDateTime.now();
    }

    public void changeUsername(PlainPassword currentPassword, Username newUsername,
                               PasswordEncoder passwordEncoder) {
        validateCurrentPassword(currentPassword, passwordEncoder);
        this.username = newUsername;
        this.modifiedAt = LocalDateTime.now();
    }

    public void changeEmail(PlainPassword currentPassword, Email newEmail,
                            PasswordEncoder passwordEncoder) {
        validateCurrentPassword(currentPassword, passwordEncoder);
        this.email = newEmail;
        this.modifiedAt = LocalDateTime.now();
    }

    // Helper methods
    public void validateCurrentPassword(PlainPassword currentPassword,
                                           PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(currentPassword, this.password)) {
            throw new PasswordMismatchException();
        }
    }

}
