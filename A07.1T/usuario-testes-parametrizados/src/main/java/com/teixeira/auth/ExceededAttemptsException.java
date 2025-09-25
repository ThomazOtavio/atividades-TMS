package com.teixeira.auth;

public class ExceededAttemptsException extends Exception {
    public ExceededAttemptsException(String message) {
        super(message);
    }
}
