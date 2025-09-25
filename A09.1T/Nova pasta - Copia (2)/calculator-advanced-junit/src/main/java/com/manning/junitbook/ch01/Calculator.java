package com.manning.junitbook.ch01;

/**
 * Calculator supporting add, subtract, multiply, divide, sqrt and pow.
 * Invalid inputs (NaN) are rejected. Division by zero throws IllegalArgumentException.
 * sqrt(x) for x < 0 throws IllegalArgumentException.
 * pow(base, exp) rules:
 *  - base < 0 requires exp to be an integer (e.g., -2^3 = -8; -2^2 = 4).
 *  - 0^exp is allowed only for exp > 0.
 *  - 0^0 and 0^negativeExp are rejected.
 *  - Overflow/underflow to Infinity/NaN is rejected with ArithmeticException.
 */
public class Calculator {

    private void validate(double a) {
        if (Double.isNaN(a)) {
            throw new IllegalArgumentException("Input must be a real number (not NaN).");
        }
    }

    private void validate(double a, double b) {
        validate(a);
        validate(b);
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

    public double sqrt(double x) {
        validate(x);
        if (x < 0.0) {
            throw new IllegalArgumentException("Square root of negative numbers is not allowed.");
        }
        double result = Math.sqrt(x);
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Result out of range.");
        }
        return result;
    }

    public double pow(double base, double exp) {
        validate(base, exp);
        // Rules for zero base
        if (base == 0.0) {
            if (exp <= 0.0) {
                throw new IllegalArgumentException("0 to non-positive exponent is undefined.");
            }
            return 0.0;
        }
        // Negative base requires integer exponent
        if (base < 0.0 && !isInteger(exp)) {
            throw new IllegalArgumentException("Negative base requires an integer exponent.");
        }
        double result = Math.pow(base, exp);
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Result out of range.");
        }
        return result;
    }

    private boolean isInteger(double x) {
        // Consider a small epsilon for floating point rounding
        double nearest = Math.rint(x);
        return Math.abs(x - nearest) < 1e-12;
    }
}
