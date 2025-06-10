package com.filmdb.auth.auth_service.service;

import com.filmdb.auth.auth_service.dto.LoginResponse;
import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.exceptions.InvalidCredentialsException;
import com.filmdb.auth.auth_service.exceptions.PasswordMismatchException;
import com.filmdb.auth.auth_service.exceptions.UserAlreadyRegisteredException;
import com.filmdb.auth.auth_service.repository.UserRepository;
import com.filmdb.auth.auth_service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        passwordEncoder = new BCryptPasswordEncoder();
        authService = new AuthService(userRepository, passwordEncoder, jwtUtil);

    }

    @Test
    void registerUser_Success() {
        // Arrange (given)
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        User result = authService.registerUser(
                "testUser", "test@mail.com", "12345", false);

        // Assert (then)
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@mail.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_AlreadyRegistered_Throws() {
        // Arrange (given)
        when(userRepository.existsByEmailOrUsername("test@mail.com", "testUser")).thenReturn(true);

        // Act (when)
        UserAlreadyRegisteredException e = assertThrows(UserAlreadyRegisteredException.class, () ->
                authService.registerUser("testUser", "test@mail.com", "12345",
                        false));

        // Assert (then)
        assertEquals("Username or email already registered.", e.getMessage());
    }

    @Test
    void loginUser_Success() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(savedUser));

        // Act (when)
        LoginResponse response = authService.loginUser("test@mail.com", "12345");

        // Assert (then)
        assertEquals(savedUser.getUsername(),response.getUsername());
    }

    @Test
    void loginUser_PasswordIsWrong_Throws() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(savedUser));

        // Act (when)
        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class, () ->
                authService.loginUser("test@mail.com", "54321"));

        // Assert (then)
        assertEquals("Invalid password.", e.getMessage());
    }

    @Test
    void loginUser_EmailIsWrong_Throws() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(savedUser));

        // Act (when)
        InvalidCredentialsException e = assertThrows(InvalidCredentialsException.class, () ->
                authService.loginUser("fake@mail.com", "12345"));

        // Assert (then)
        assertEquals("No user found with that email.", e.getMessage());
    }

    @Test
    void changeUserPassword_Success() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        authService.changeUserPassword(savedUser, "12345", "secure");

        // Assert (then)
        assertTrue(passwordEncoder.matches("secure", savedUser.getPassword()));
    }

    @Test
    void changeUserPassword_currentPasswordMismatch_Throws() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        PasswordMismatchException e = assertThrows(PasswordMismatchException.class, () ->
                authService.changeUserPassword(savedUser, "54321", "securePwd"));

        // Assert (then)
        assertEquals("Current password doesn't match.", e.getMessage());
    }

    @Test
    void changeUserUsername_Success() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        authService.changeUserUsername(savedUser, "12345", "coolUser");

        // Assert (then)
        assertEquals("coolUser", savedUser.getUsername());
    }

    @Test
    void changeUserUsername_currentPasswordMismatch_Throws() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        PasswordMismatchException e = assertThrows(PasswordMismatchException.class, () ->
                authService.changeUserUsername(savedUser, "54321", "coolUser"));

        // Assert (then)
        assertEquals("Current password doesn't match.", e.getMessage());
    }

    @Test
    void changeUserEmail_Success() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        authService.changeUserEmail(savedUser, "12345", "new@mail.com");

        // Assert (then)
        assertEquals("new@mail.com", savedUser.getEmail());
    }

    @Test
    void changeUserEmail_currentPasswordMismatch_Throws() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        PasswordMismatchException e = assertThrows(PasswordMismatchException.class, () ->
                authService.changeUserEmail(savedUser, "54321", "new@mail.com"));

        // Assert (then)
        assertEquals("Current password doesn't match.", e.getMessage());
    }

    @Test
    void deleteUser_Success() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        authService.deleteUser(savedUser, "12345");

        // Assert (then)
        verify(userRepository).delete(savedUser);
    }

    @Test
    void deleteUser_currentPasswordMismatch_Throws() {
        // Arrange (given)
        User savedUser = new User();
        savedUser.setId("2febf143-8fd3-4bb0-b87d-ba03c8422605");
        savedUser.setEmail("test@mail.com");
        savedUser.setUsername("testUser");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act (when)
        PasswordMismatchException e = assertThrows(PasswordMismatchException.class, () ->
                authService.deleteUser(savedUser, "54321"));

        // Assert (then)
        assertEquals("Current password doesn't match.", e.getMessage());
    }

}
