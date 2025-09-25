package com.teixeira.tdd.password;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ValidationResult {
    private final boolean valid;
    private final EnumSet<ValidationError> errors;

    private ValidationResult(boolean valid, EnumSet<ValidationError> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    public static ValidationResult ok() {
        return new ValidationResult(true, EnumSet.noneOf(ValidationError.class));
    }

    public static ValidationResult fail(Set<ValidationError> errors) {
        EnumSet<ValidationError> copy = errors.isEmpty()
                ? EnumSet.noneOf(ValidationError.class)
                : EnumSet.copyOf(errors);
        return new ValidationResult(false, copy);
    }

    public boolean isValid() { return valid; }

    public Set<ValidationError> getErrors() {
        return Collections.unmodifiableSet(errors);
    }

    @Override
    public String toString() {
        return "ValidationResult{valid=" + valid + ", errors=" + errors + "}";
    }
}
