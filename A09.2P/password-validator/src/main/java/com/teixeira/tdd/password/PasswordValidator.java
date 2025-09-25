package com.teixeira.tdd.password;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern UPPER = Pattern.compile("[A-Z]");
    private static final Pattern LOWER = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("\\d");
    private static final Pattern WHITESPACE = Pattern.compile("\\s");
    // special char is anything that's not letter, digit or whitespace
    private static final Pattern SPECIAL = Pattern.compile("[^A-Za-z0-9\\s]");

    private final PasswordPolicy policy;

    public PasswordValidator() {
        this(PasswordPolicy.defaultPolicy());
    }

    public PasswordValidator(PasswordPolicy policy) {
        this.policy = policy;
    }

    /** Valida a senha de acordo com a política. */
    public ValidationResult validate(String password) {
        EnumSet<ValidationError> errors = EnumSet.noneOf(ValidationError.class);

        if (password == null || password.isEmpty()) {
            errors.add(ValidationError.NULL_OR_EMPTY);
            return ValidationResult.fail(errors);
        }

        if (password.length() < policy.getMinLength()) {
            errors.add(ValidationError.TOO_SHORT);
        }

        if (policy.isNoWhitespace() && WHITESPACE.matcher(password).find()) {
            errors.add(ValidationError.HAS_WHITESPACE);
        }

        if (policy.isRequireUppercase() && !UPPER.matcher(password).find()) {
            errors.add(ValidationError.NO_UPPERCASE);
        }
        if (policy.isRequireLowercase() && !LOWER.matcher(password).find()) {
            errors.add(ValidationError.NO_LOWERCASE);
        }
        if (policy.isRequireDigit() && !DIGIT.matcher(password).find()) {
            errors.add(ValidationError.NO_DIGIT);
        }
        if (policy.isRequireSpecial() && !SPECIAL.matcher(password).find()) {
            errors.add(ValidationError.NO_SPECIAL);
        }

        if (errors.isEmpty()) return ValidationResult.ok();
        return ValidationResult.fail(errors);
    }

    /** Conveniência: retorna apenas booleano. */
    public boolean isValid(String password) {
        return validate(password).isValid();
    }
}
