package com.teixeira.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MessageDigestPasswordHasher implements PasswordHasher {
    private final String algorithm;

    public MessageDigestPasswordHasher(String algorithm) {
        if (algorithm == null || algorithm.isBlank()) {
            throw new IllegalArgumentException("Algorithm must not be null/blank");
        }
        this.algorithm = algorithm;
    }

    @Override
    public String hash(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password must not be null");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Unsupported hash algorithm: " + algorithm, e);
        }
    }
}
