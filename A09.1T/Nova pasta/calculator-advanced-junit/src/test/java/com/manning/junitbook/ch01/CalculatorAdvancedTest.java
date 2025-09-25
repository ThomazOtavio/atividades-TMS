package com.manning.junitbook.ch01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Advanced Operations")
class CalculatorAdvancedTest {

    private final Calculator calc = new Calculator();

    // --- SQRT TESTS ---------------------------------------------------------
    @Nested
    @DisplayName("sqrt(x)")
    class SqrtTests {

        @ParameterizedTest(name = "sqrt({0}) = {1}")
        @CsvSource({
                "0.0, 0.0",
                "1.0, 1.0",
                "4.0, 2.0",
                "2.25, 1.5",
                "1e-308, 1e-154",
                "1e308, 1e154"
        })
        void sqrt_valid(double input, double expected) {
            assertEquals(expected, calc.sqrt(input), 1e-12);
        }

        @Test
        void sqrt_negative_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.sqrt(-1.0));
        }
    }

    // --- POW TESTS ----------------------------------------------------------
    @Nested
    @DisplayName("pow(base, exp)")
    class PowTests {

        @ParameterizedTest(name = "pow({0}, {1}) = {2}")
        @CsvSource({
                "2, 3, 8",
                "2, -2, 0.25",
                "9, 0.5, 3",
                "-2, 3, -8",
                "-2, 2, 4",
                "0, 5, 0"
        })
        void pow_valid_various(double base, double exp, double expected) {
            assertEquals(expected, calc.pow(base, exp), 1e-12);
        }

        static Stream<double[]> powBoundaryProvider() {
            return Stream.of(
                    new double[]{Double.MAX_VALUE, 1, Double.MAX_VALUE}, // ok
                    new double[]{10, 308, 1e308} // ok, still finite
            );
        }

        @ParameterizedTest(name = "boundary: pow({0}, {1}) ≈ {2}")
        @MethodSource("powBoundaryProvider")
        void pow_boundaries_ok(double base, double exp, double expected) {
            assertEquals(expected, calc.pow(base, exp), Math.abs(expected) * 1e-12 + 1e-12);
        }

        @Test
        void pow_zero_to_zero_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.pow(0.0, 0.0));
        }

        @Test
        void pow_zero_to_negative_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.pow(0.0, -1.0));
        }

        @Test
        void pow_negative_base_fractional_exponent_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.pow(-2.0, 0.5));
        }

        @Test
        void pow_overflow_throws() {
            assertThrows(ArithmeticException.class, () -> calc.pow(Double.MAX_VALUE, 2.0)); // Infinity
        }
    }

    // --- DYNAMIC TESTS ------------------------------------------------------
    @TestFactory
    @DisplayName("Dynamic identity tests: sqrt(pow(x, 2)) == |x| for sample values")
    List<DynamicTest> dynamic_identity_tests() {
        double[] samples = { -10.0, -2.5, -1.0, 0.0, 2.0, 3.5, 10.0 };
        return java.util.Arrays.stream(samples).mapToObj(x ->
            DynamicTest.dynamicTest("x=" + x, () -> {
                double squared = calc.multiply(x, x);
                double rooted = calc.sqrt(squared);
                assertEquals(Math.abs(x), rooted, 1e-12);
            })
        ).toList();
    }

    // --- INVALID INPUTS (NaN) -----------------------------------------------
    @Nested
    class InvalidInputs {
        @Test
        void sqrt_withNaN_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.sqrt(Double.NaN));
        }

        @Test
        void pow_withNaN_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.pow(Double.NaN, 2));
            assertThrows(IllegalArgumentException.class, () -> calc.pow(2, Double.NaN));
        }
    }

    // --- ASSERT ALL EXAMPLE -------------------------------------------------
    @Test
    void combined_properties_example() {
        assertAll("basic arithmetic properties (selected)",
                () -> assertEquals(5.0, calc.add(2, 3), 1e-12),
                () -> assertEquals(-1.0, calc.subtract(2, 3), 1e-12),
                () -> assertEquals(6.0, calc.multiply(2, 3), 1e-12),
                () -> assertEquals(2.0, calc.sqrt(4.0), 1e-12)
        );
    }
}
