package com.teixeira.tdd.password;

public enum ValidationError {
    NULL_OR_EMPTY,
    TOO_SHORT,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_DIGIT,
    NO_SPECIAL,
    HAS_WHITESPACE
}
