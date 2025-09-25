package com.manning.junitbook.ch01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@DisplayName("TDD – Divisão (divide)")
class CalculatorDivideExtendedTest {

    private final Calculator calc = new Calculator();

    @Nested
    @DisplayName("Casos básicos e propriedades")
    class Basics {

        @ParameterizedTest(name = "divide({0}, {1}) ≈ {2}")
        @CsvSource(textBlock = """
            6, 3, 2
            -6, 3, -2
            6, -3, -2
            -6, -3, 2
            0, 5, 0
            10, 2.5, 4
            1e-12, 1e-6, 1e-6
        """)
        void regular_pairs(double a, double b, double expected) {
            assertThat(calc.divide(a, b), closeTo(expected, 1e-12));
        }

        @Test
        @DisplayName("Identidade: x / 1 = x")
        void identity() {
            assertEquals(123.456, calc.divide(123.456, 1.0), 0.0);
        }
    }

    @Nested
    @DisplayName("Extremos numéricos (documentar comportamento)")
    class Extremes {

        @Test
        @DisplayName("MAX_VALUE / 2 é finito")
        void max_over_two_is_finite() {
            assertFalse(Double.isInfinite(calc.divide(Double.MAX_VALUE, 2.0)));
        }

        @Test
        @DisplayName("1 / MIN_VALUE → Infinity (estouro esperado)")
        void one_over_min_value_is_infinity() {
            assertTrue(Double.isInfinite(calc.divide(1.0, Double.MIN_VALUE)));
        }
    }

    @Nested
    @DisplayName("Entradas inválidas – exceções")
    class InvalidInputs {

        @Test
        @DisplayName("Divisão por zero lança IllegalArgumentException")
        void division_by_zero() {
            assertThrows(IllegalArgumentException.class, () -> calc.divide(1.0, 0.0));
        }

        @ParameterizedTest(name = "NaN em divide({0}, {1}) deve lançar IllegalArgumentException")
        @CsvSource(textBlock = """
            NaN, 1
            1, NaN
            NaN, NaN
        """)
        void rejects_nan(Double a, Double b) {
            assertThrows(IllegalArgumentException.class, () -> calc.divide(a, b));
        }

        @Disabled("Ative após endurecer validação para Infinity")
        @ParameterizedTest(name = "Infinity em divide({0}, {1}) deve lançar IllegalArgumentException")
        @CsvSource(textBlock = """
            Infinity, 1
            -Infinity, 1
            1, Infinity
            1, -Infinity
        """)
        void rejects_infinities(Double a, Double b) {
            assertThrows(IllegalArgumentException.class, () -> calc.divide(a, b));
        }
    }
}
