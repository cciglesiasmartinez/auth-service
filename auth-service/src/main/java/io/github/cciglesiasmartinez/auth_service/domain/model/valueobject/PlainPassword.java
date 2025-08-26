package io.github.cciglesiasmartinez.auth_service.domain.model.valueobject;

import io.github.cciglesiasmartinez.auth_service.domain.exception.InvalidCredentialsException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.InvalidPasswordException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Value Object representing a valid plain password.
 * <p>
 * A plain password must be a non-null, non-empty string with a length between {@code MIN_LENGTH} and
 * {@code MAX_LENGTH} . It should contain at least one uppercase character, one lowercase character, one number
 * and one special character. Cannot contain spaces.
 */
@EqualsAndHashCode
@ToString
public final class PlainPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 50;

    private final String value;

    private PlainPassword(String value) {
        this.value = value;
    }

    /**
     * Validates a given password against a set of rules leveraging Passay's {@link PasswordValidator}.
     *
     * @param value password {@code String} to validate.
     * @throws InvalidPasswordException if validation fails.
     */
    private static void validatePassword(String value) {
        List<Rule> rules = new ArrayList<>();
        rules.add(new LengthRule(MIN_LENGTH, MAX_LENGTH));
        rules.add(new WhitespaceRule());
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));
        PasswordValidator validator = new PasswordValidator(rules);
        PasswordData password = new PasswordData(value);
        RuleResult result = validator.validate(password);
        if (!result.isValid()) {
            throw new InvalidPasswordException(String.join(" ", validator.getMessages(result)));
        }
    }

    /**
     * Creates a {@code PlainPassword} instance from a raw string.
     *
     * @param password the raw password string.
     * @return a validated {@code PlainPassword} instance.
     * @throws InvalidPasswordException if the password is null or empty.
     */
    public static PlainPassword of(String password) {
        if (password == null) {
            throw new InvalidPasswordException("Password cannot be null.");
        }
        String trimmedPassword = password.trim();
        if (trimmedPassword.isEmpty()) {
            throw new InvalidPasswordException("Password cannot be empty.");
        }
        validatePassword(trimmedPassword);
        return new PlainPassword(trimmedPassword);
    }

    /**
     * Factory method intended to use in LoginUserUserCase. It basically follows the same rules as .of but avoids
     * unnecessary password (format) validation and throws generic {@link InvalidCredentialsException} exceptions
     * suitable for login flows. This way we avoid potentially nasty scenarios.
     *
     * @param password the raw password string.
     * @return a validated {@code PlainPassword} instance.
     * @throws InvalidCredentialsException if the password is null or empty.
     */
    public static PlainPassword forLogin(String password) {
        if (password == null) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        String trimmedPassword = password.trim();
        if (trimmedPassword.isEmpty()) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        return new PlainPassword(trimmedPassword);
    }

    /**
     * Returns the raw string value of the plain password.
     *
     * @return the plain password string.
     */
    public String value() {
        return value;
    }

}
