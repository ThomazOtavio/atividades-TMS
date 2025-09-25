package com.manning.junitbook.ch01;

/**
 * Simple calculator supporting add, subtract, multiply and divide.
 * For safety in tests, this implementation rejects NaN inputs and division by zero.
 */
public class Calculator {

    private void validate(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new IllegalArgumentException("Inputs must be real numbers (not NaN).");
        }
    }

    public double add(double a, double b) {
        validate(a, b);
        return a + b;
    }

    public double subtract(double a, double b) {
        validate(a, b);
        return a - b;
    }

    public double multiply(double a, double b) {
        validate(a, b);
        return a * b;
    }

    public double divide(double a, double b) {
        validate(a, b);
        if (b == 0.0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return a / b;
    }
}
