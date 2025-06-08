package com.filmdb.auth.auth_service.service;

import com.filmdb.auth.auth_service.dto.LoginResponse;
import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.repository.UserRepository;
import com.filmdb.auth.auth_service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    // This is the UserRepository we're going to use
    private final UserRepository userRepository;

    // Utility for encrypting passwords
    private final BCryptPasswordEncoder passwordEncoder;

    // Helper class for JWT
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    /**
     * Helper method to check if a user is already registered.
     * @param username The username for the desired user to check.
     * @param email The email for the desired user to check.
     * @return {@code true} if is not registered, {@code false} if it is.
     */
    public boolean isUserAlreadyRegistered(String username, String email) {
        return (userRepository.findByEmail(email).isPresent()) || (userRepository.findByUsername(username).isPresent());
    }

    /**
     * Creates a new user entry in the database.
     * @param username The username for the user.
     * @param email The email for the user.
     * @param rawPassword Unencrypted password for the user.
     * @param isAdmin {@code true} if the user is going to be admin, {@code false} if it's not.
     * @return An object containing the data pertaining to the new user.
     * @throws IllegalArgumentException if the username or email are already registered.
     */
    public User registerUser(String username, String email, String rawPassword, boolean isAdmin) {
        if (!this.isUserAlreadyRegistered(username, email)) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setAdmin(false); // Might change this in the DTO
            user.setRegisteredAt(java.time.LocalDateTime.now());
            user.setModifiedAt(java.time.LocalDateTime.now());
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Username or email already registered.");
        }
    }

    /**
     * Returns the JWT for the desired user
     * @param email User's email
     * @param rawPassword User's raw password
     * @return JWT in String format
     * @throws IllegalArgumentException if password or email mismatch.
     */
    public LoginResponse loginUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("No user found with that email.");
        }
        User user = userOptional.get();
        String encodedPassword = user.getPassword();
        if (passwordEncoder.matches(rawPassword,encodedPassword)) {
            String token = jwtUtil.generateToken(user.getUsername());
            long expiresIn = jwtUtil.getJwtExpirationMs() / 1000;
            return new LoginResponse(token, expiresIn, user.getUsername());
        } else {
            throw new IllegalArgumentException("Invalid password.");
        }
    }

    /**
     * Changes user password.
     * @param user The user object received from the controller
     * @param currentPassword Current password for the user (from the request)
     * @param newPassword New password for the user (from the request)
     * @throws IllegalArgumentException if current password in the request doesn't match the one in database
     */
    public void changeUserPassword(User user, String currentPassword, String newPassword) {
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setModifiedAt(java.time.LocalDateTime.now());
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Current password doesn't match.");
        }
    }

    /**
     *
     * @param user
     * @param username
     * @throws IllegalArgumentException
     */
    public void changeUserUsername(User user, String currentPassword, String username) {
        // Needs length validation somewhere :) probably on DTO
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            if (userRepository.findByUsername(username).isEmpty()) {
                user.setUsername(username);
                user.setModifiedAt(java.time.LocalDateTime.now());
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Username already exists.");
            }
        } else {
            throw new IllegalArgumentException("Current password doesn't match.");
        }
    }

    /**
     *
     * @param user
     * @param email
     * @throws IllegalArgumentException
     */
    public void changeUserEmail(User user, String currentPassword, String email) {
        // Needs length and format validation somewhere :) probably on DTO
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            if (userRepository.findByEmail(email).isEmpty()) {
                user.setEmail(email);
                user.setModifiedAt(java.time.LocalDateTime.now());
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Email already exists.");
            }
        } else {
            throw new IllegalArgumentException("Current password doesn't match.");
        }
    }

    /**
     *
     * @param user
     * @param currentPassword
     * @throws IllegalArgumentException
     */
    public void deleteUser(User user, String currentPassword) {
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setModifiedAt(java.time.LocalDateTime.now());
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("Current password doesn't match.");
        }
    }

}
