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

@DisplayName("TDD – Raiz quadrada (sqrt)")
class CalculatorSqrtExtendedTest {

    private final Calculator calc = new Calculator();

    @Nested
    @DisplayName("Casos básicos & propriedades")
    class Basics {

        @ParameterizedTest(name = "sqrt({0}) ≈ {1}")
        @CsvSource(textBlock = """
            0, 0
            1, 1
            4, 2
            9, 3
            0.25, 0.5
            2.25, 1.5
        """)
        void perfect_and_decimal_squares(double x, double expected) {
            assertThat(calc.sqrt(x), closeTo(expected, 1e-12));
        }

        @ParameterizedTest(name = "Propriedade: (sqrt({0}))^2 ≈ {0}")
        @CsvSource(textBlock = """
            0
            1
            2
            10.5
            1e-12
            1e6
        """)
        void square_of_sqrt_returns_original(double x) {
            double r = calc.sqrt(x);
            assertThat(r * r, closeTo(x, 1e-12));
        }
    }

    @Nested
    @DisplayName("Extremos numéricos (documentar comportamento)")
    class Extremes {
        @Test
        @DisplayName("sqrt(MAX_VALUE) é finito e > 0")
        void sqrt_max_value_is_finite() {
            double r = calc.sqrt(Double.MAX_VALUE);
            assertTrue(r > 0.0 && !Double.isInfinite(r) && !Double.isNaN(r));
        }

        @Test
        @DisplayName("sqrt(MIN_VALUE) > 0")
        void sqrt_min_value_positive() {
            double r = calc.sqrt(Double.MIN_VALUE);
            assertTrue(r > 0.0);
        }
    }

    @Nested
    @DisplayName("Entradas inválidas – exceções")
    class InvalidInputs {

        @Test
        @DisplayName("sqrt(x) para x < 0 lança IllegalArgumentException")
        void sqrt_negative_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.sqrt(-1.0));
        }

        @Test
        @DisplayName("sqrt(NaN) lança IllegalArgumentException")
        void sqrt_nan_throws() {
            assertThrows(IllegalArgumentException.class, () -> calc.sqrt(Double.NaN));
        }

        @Disabled("Ative após endurecer validação para Infinity")
        @Test
        @DisplayName("sqrt(Infinity) deve lançar IllegalArgumentException (após refatoração)")
        void sqrt_infinity_rejected() {
            assertThrows(IllegalArgumentException.class, () -> calc.sqrt(Double.POSITIVE_INFINITY));
        }
    }
}
