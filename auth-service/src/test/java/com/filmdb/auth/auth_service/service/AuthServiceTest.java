package com.filmdb.auth.auth_service.service;

import com.filmdb.auth.auth_service.entity.User;
import com.filmdb.auth.auth_service.repository.UserRepository;
import com.filmdb.auth.auth_service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        authService = new AuthService(userRepository, jwtUtil);
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
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(new User()));

        // Act (when)
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                authService.registerUser("testUser", "test@mail.com", "12345",
                        false));

        // Assert (then)
        assertEquals("Username or email already registered.", e.getMessage());
    }

}
