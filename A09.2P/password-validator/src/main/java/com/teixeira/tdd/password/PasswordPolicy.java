package com.teixeira.tdd.password;

public class PasswordPolicy {
    private final int minLength;
    private final boolean requireUppercase;
    private final boolean requireLowercase;
    private final boolean requireDigit;
    private final boolean requireSpecial;
    private final boolean noWhitespace;

    public PasswordPolicy() {
        this(8, true, true, true, true, true);
    }

    public PasswordPolicy(int minLength, boolean requireUppercase, boolean requireLowercase,
                          boolean requireDigit, boolean requireSpecial, boolean noWhitespace) {
        this.minLength = minLength;
        this.requireUppercase = requireUppercase;
        this.requireLowercase = requireLowercase;
        this.requireDigit = requireDigit;
        this.requireSpecial = requireSpecial;
        this.noWhitespace = noWhitespace;
    }

    public int getMinLength() { return minLength; }
    public boolean isRequireUppercase() { return requireUppercase; }
    public boolean isRequireLowercase() { return requireLowercase; }
    public boolean isRequireDigit() { return requireDigit; }
    public boolean isRequireSpecial() { return requireSpecial; }
    public boolean isNoWhitespace() { return noWhitespace; }

    public static PasswordPolicy defaultPolicy() { return new PasswordPolicy(); }
}
