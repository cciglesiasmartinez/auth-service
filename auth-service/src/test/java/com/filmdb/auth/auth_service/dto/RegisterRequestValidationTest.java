package com.filmdb.auth.auth_service.dto;

import com.filmdb.auth.auth_service.adapter.in.web.dto.requests.RegisterRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        // Arrange (given)
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setEmail("john@example.com");
        request.setPassword("securePass123");

        // Act (when)
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Assert (then)
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEmailInvalid_thenViolation() {
        // Arrange (given)
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setEmail("not-an-email");
        request.setPassword("securePass123");

        // Act (when)
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Assert (then)
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void whenUsernameBlank_thenViolation() {
        // Arrange (given)
        RegisterRequest request = new RegisterRequest();
        request.setUsername("");  // Invalid
        request.setEmail("john@example.com");
        request.setPassword("securePass123");

        // Act (when)
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Assert (then)
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void whenPasswordMissing_thenViolation() {
        // Arrange (given)
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setEmail("john@example.com");
        request.setPassword("");  // Invalid

        // Act (when)
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Assert (then)
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}
