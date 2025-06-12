package com.filmdb.auth.auth_service.service;

import com.filmdb.auth.auth_service.dto.responses.ChangeEmailResponse;
import com.filmdb.auth.auth_service.dto.responses.ChangeUsernameResponse;
import com.filmdb.auth.auth_service.dto.responses.LoginResponse;
import com.filmdb.auth.auth_service.dto.responses.UserResponse;
import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.exceptions.*;
import com.filmdb.auth.auth_service.repository.UserRepository;
import com.filmdb.auth.auth_service.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that handles authentication-related operations such as user registration, login, password
 * and profile updates, and user deletion. This class acts as the core of authentication logic, interfacing
 * with the {@link UserRepository}, password encoding utilities, and JWT token generation.
 */
@Service
public class AuthService {

    /** Repository for accessing user data from the database. */
    private final UserRepository userRepository;

    /** Utility for encoding and verifying user passwords using BCrypt. */
    private final PasswordEncoder passwordEncoder;

    /** Utility class for generating and validating JWT tokens. */
    private final JwtUtil jwtUtil;

    /**
     * Constructs an {@code AuthService} with the necessary dependencies.
     *
     * @param userRepository Repository used to perform CRUD operations on User entities.
     * @param jwtUtil Utility class used to handle JWT creation and validation.
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Helper method to validate current password before performing sensitive operations.
     *
     * @param user The user whose password we are going to validate.
     * @param currentPassword The password to validate.
     * @throws PasswordMismatchException if password doesn't match.
     */
    private void validateCurrentPassword(User user, String currentPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    /**
     * Helper method that checks if a user is already registered.
     *
     * @param username The username for the desired user to check.
     * @param email The email for the desired user to check.
     * @return {@code true} if is not registered, {@code false} if it is.
     */
    private boolean isUserAlreadyRegistered(String username, String email) {
        return userRepository.existsByEmailOrUsername(email, username);
    }

    /**
     * Creates a new user entry in the database.
     *
     * @param username The username for the user.
     * @param email The email for the user.
     * @param rawPassword Unencrypted password for the user.
     * @param isAdmin {@code true} if the user is going to be admin, {@code false} if it's not.
     * @return An object containing the data pertaining to the new user.
     * @throws UserAlreadyRegisteredException if the username or email are already registered.
     */
    @Transactional
    public UserResponse registerUser(String username, String email, String rawPassword, boolean isAdmin) {
        if (!isUserAlreadyRegistered(username, email)) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setAdmin(isAdmin);
            user.setRegisteredAt(java.time.LocalDateTime.now());
            userRepository.save(user);
            return UserResponse.fromUser(user);
        } else {
            throw new UserAlreadyRegisteredException();
        }
    }

    /**
     * Authenticates a user using their email and raw (plaintext) password. If the credentials are valid, returns
     * a {@link LoginResponse} containing a signed JWT, the token expiration time (in seconds), and the username.
     *
     * @param email The email address of the user attempting to log in.
     * @param rawPassword The plaintext password provided by the user.
     * @return A {@link LoginResponse} object containing the JWT, expiration time, and username.
     * @throws InvalidCredentialsException if email or password are not correct.
     */
    public LoginResponse loginUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("No user found with that email.");
        }
        User user = userOptional.get();
        String encodedPassword = user.getPassword();
        if (passwordEncoder.matches(rawPassword,encodedPassword)) {
            String token = jwtUtil.generateToken(user.getUsername());
            long expiresIn = jwtUtil.getJwtExpirationMs() / 1000;
            return new LoginResponse(token, expiresIn, user.getUsername());
        } else {
            throw new InvalidCredentialsException("Invalid password.");
        }
    }

    /**
     * Changes the password of a given user if the current password is correct.
     *
     * @param user The user object whose password will be changed.
     * @param currentPassword The current password to verify the user's identity.
     * @param newPassword The new desired password.
     * @throws PasswordMismatchException if the current password provided does not match the stored password.
     */
    @Transactional
    public void changeUserPassword(User user, String currentPassword, String newPassword) {
        validateCurrentPassword(user, currentPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Changes the username of a given user if the current password is correct and new username is not already taken.
     *
     * @param user The user object whose username will be changed.
     * @param currentPassword The current password to verify the user's identity.
     * @param username The new desired username.
     * @throws UsernameAlreadyExistsException if the new username is already in use by another user.
     * @throws PasswordMismatchException if the current password provided does not match the stored password.
     */
    @Transactional
    public ChangeUsernameResponse changeUserUsername(User user, String currentPassword, String username) {
        validateCurrentPassword(user, currentPassword);
        if (userRepository.findByUsername(username).isEmpty()) {
            user.setUsername(username);
            userRepository.save(user);
            return new ChangeUsernameResponse(username, LocalDateTime.now());
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    /**
     * Changes the email of a given user if the current password is correct and new email is not already registered.
     *
     * @param user The user object whose email will be changed.
     * @param currentPassword The current password to verify the user's identity.
     * @param email The new desired email address.
     * @throws EmailAlreadyExistsException if the new email is already in use by another user.
     * @throws PasswordMismatchException if the current password provided does not match the stored password.
     */
    @Transactional
    public ChangeEmailResponse changeUserEmail(User user, String currentPassword, String email) {
        validateCurrentPassword(user, currentPassword);
        if (userRepository.findByEmail(email).isEmpty()) {
            user.setEmail(email);
            userRepository.save(user);
            return new ChangeEmailResponse(email, LocalDateTime.now());
        } else {
            throw new EmailAlreadyExistsException();
        }
    }

    /**
     * Deletes a user account if the current password is verified correctly.
     *
     * @param user The user object to be deleted.
     * @param currentPassword The current password to verify the user's identity before deletion.
     * @throws PasswordMismatchException if the current password provided does not match the stored password.
     */
    @Transactional
    public void deleteUser(User user, String currentPassword) {
        validateCurrentPassword(user, currentPassword);
        userRepository.delete(user);
    }

}
