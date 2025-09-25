package com.teixeira.auth;

public interface PasswordHasher {
    String hash(String rawPassword);
}
