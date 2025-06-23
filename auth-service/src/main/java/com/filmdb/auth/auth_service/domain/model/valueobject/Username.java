package com.filmdb.auth.auth_service.domain.model.valueobject;

import com.filmdb.auth.auth_service.domain.exception.InvalidUsernameException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Value Object representing a valid username.
 * <p>
 * A username must be a non-null, non-empty string with a length between 3 and 30 characters. It shouldn't contain
 * special characters either.
 * <p>
 * Leading and trailing whitespace is trimmed before validation.
 */
@EqualsAndHashCode
@ToString
public final class Username {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;

    private final String value;

    private Username(String value) {
        this.value = value;
    }

    /**
     * Validates a given username against a set of rules leveraging Passay's {@link PasswordValidator}.
     *
     * @param value username {@code String} to validate.
     * @throws InvalidUsernameException if validation fails.
     */
    private static void validateUsername(String value) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(MIN_LENGTH, MAX_LENGTH));
        rules.add(new WhitespaceRule());
        rules.add(new AllowedRegexRule("^[a-zA-Z0-9]+$"));
        PasswordValidator validator = new PasswordValidator(rules);
        PasswordData password = new PasswordData(value);
        RuleResult result = validator.validate(password);
        if (!result.isValid()) {
            // TODO: Create a mapper function for errors or maybe a custom MessageResolver (Passay)
            throw new InvalidUsernameException(String.join(" ", validator.getMessages(result)).
                    replace("Password", "Username"));
        }
    }

    /**
     * Creates a {@code Username} instance from a raw string.
     *
     * @param username the raw username string
     * @return a validated {@code Username} instance
     * @throws InvalidUsernameException if the username is null or empty.
     */
    public static Username of(String username) {
        if (username == null) {
            throw new InvalidUsernameException("Username cannot be null.");
        }
        String trimmedUsername = username.trim();
        if (trimmedUsername.isEmpty()) {
            throw new InvalidUsernameException("Username cannot be empty.");
        }
        validateUsername(trimmedUsername);
        return new Username(trimmedUsername);
    }

    /**
     * Returns the raw string value of the username.
     *
     * @return the username string
     */
    public String value() {
        return value;
    }

}
