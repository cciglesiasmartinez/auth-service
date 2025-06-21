package com.filmdb.auth.auth_service.domain.model.valueobject;

import com.filmdb.auth.auth_service.domain.exception.InvalidPasswordException;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.passay.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public final class PlainPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 50;

    private final String value;

    private PlainPassword(String value) {
        this.value = value;
    }

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

    public static PlainPassword of(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException("Password cannot be null or empty.");
        }
        validatePassword(password);
        return new PlainPassword(password);
    }

    public String value() {
        return value;
    }

}
