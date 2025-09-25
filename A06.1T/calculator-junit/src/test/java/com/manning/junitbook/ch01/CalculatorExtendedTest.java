package com.manning.junitbook.ch01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests covering subtraction, multiplication, and division with multiple scenarios.
 */
class CalculatorExtendedTest {

    @ParameterizedTest(name = "add({0}, {1}) = {2}")
    @CsvSource({
            "2, 3, 5",
            "-5, 8, 3",
            "0, 0, 0",
            "1.5, 2.5, 4.0",
            "1e6, 2e6, 3e6"
    })
    void testAdd_multipleCases(double a, double b, double expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.add(a, b), 1e-9);
    }

    @ParameterizedTest(name = "subtract({0}, {1}) = {2}")
    @CsvSource({
            "5, 2, 3",
            "2, 5, -3",
            "0, 0, 0",
            "2.5, 1.2, 1.3",
            "-4, -6, 2"
    })
    void testSubtract_multipleCases(double a, double b, double expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.subtract(a, b), 1e-9);
    }

    @ParameterizedTest(name = "multiply({0}, {1}) = {2}")
    @CsvSource({
            "3, 4, 12",
            "3, 0, 0",
            "2.5, 2, 5.0",
            "-2, -8, 16",
            "-2, 8, -16"
    })
    void testMultiply_multipleCases(double a, double b, double expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.multiply(a, b), 1e-9);
    }

    @ParameterizedTest(name = "divide({0}, {1}) = {2}")
    @CsvSource({
            "10, 2, 5",
            "-9, 3, -3",
            "5, -2, -2.5",
            "2.5, 0.5, 5.0"
    })
    void testDivide_multipleCases(double a, double b, double expected) {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.divide(a, b), 1e-9);
    }

    @Test
    void testDivide_byZero_throws() {
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calc.divide(10, 0));
    }

    @Test
    void testAdd_withNaN_throws() {
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calc.add(Double.NaN, 1));
        assertThrows(IllegalArgumentException.class, () -> calc.add(1, Double.NaN));
    }
}
