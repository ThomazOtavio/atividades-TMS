package com.manning.junitbook.ch01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

@DisplayName("Calculator.add – casos estendidos")
class CalculatorAddExtendedTest {

    private final Calculator calc = new Calculator();

    // ------------------------------------------------------------
    @Nested
    @DisplayName("Faixas de valores válidos (positivos, negativos e zero)")
    class ValidRanges {

        @ParameterizedTest(name = "add({0}, {1}) = {2}")
        @CsvSource(textBlock = """
            0, 0, 0
            1, 2, 3
            -1, -2, -3
            -3, 3, 0
            1000, -1, 999
            -1000, 1, -999
            1e-12, -1e-12, 0
        """)
        void simple_pairs(double a, double b, double expected) {
            double result = calc.add(a, b);
            assertEquals(expected, result, 1e-12);
        }

        @RepeatedTest(5)
        @DisplayName("Identidade: x + 0 = x")
        void identity_property() {
            double x = 123.456; // determinístico
            assertEquals(x, calc.add(x, 0.0), 0.0);
            assertEquals(x, calc.add(0.0, x), 0.0);
        }

        @Test
        @DisplayName("Comutatividade: a + b = b + a")
        void commutativity() {
            double a = -42.5, b = 19.25;
            assertEquals(calc.add(a, b), calc.add(b, a), 0.0);
        }
    }

    // ------------------------------------------------------------
    @Nested
    @DisplayName("Valores decimais (comparação com tolerância)")
    class Decimals {

        @ParameterizedTest(name = "decimals: add({0}, {1}) ≈ {2}")
        @CsvSource(textBlock = """
            0.1, 0.2, 0.3
            1.2345, 2.3456, 3.5801
            -0.7, 0.1, -0.6
            1234.5678, 0.0003, 1234.5681
        """)
        void decimals_close_to(double a, double b, double expected) {
            double result = calc.add(a, b);
            // Hamcrest deixa o erro de aproximação mais legível
            assertThat(result, closeTo(expected, 1e-12));
        }
    }

    // ------------------------------------------------------------
    @Nested
    @DisplayName("Valores extremos e limites numéricos")
    class Extremes {

        @Test
        @DisplayName("MAX_VALUE + 1 = MAX_VALUE (perda de precisão; ainda finito)")
        void max_plus_one_is_still_max_due_to_rounding() {
            double result = calc.add(Double.MAX_VALUE, 1.0);
            assertEquals(Double.MAX_VALUE, result, 0.0);
        }

        @Test
        @DisplayName("MAX_VALUE + MAX_VALUE → Infinity (comportamento atual a documentar)")
        void overflow_results_in_infinity_today() {
            double result = calc.add(Double.MAX_VALUE, Double.MAX_VALUE);
            assertTrue(Double.isInfinite(result));
        }

        @Test
        @DisplayName("(-MAX_VALUE) + (-MAX_VALUE) → -Infinity (comportamento atual a documentar)")
        void underflow_results_in_negative_infinity_today() {
            double result = calc.add(-Double.MAX_VALUE, -Double.MAX_VALUE);
            assertTrue(Double.isInfinite(result) && result < 0);
        }
    }

    // ------------------------------------------------------------
    @Nested
    @DisplayName("Entradas inválidas – exceções")
    class InvalidInputs {

        @ParameterizedTest(name = "NaN em add({0}, {1}) deve lançar IllegalArgumentException")
        @CsvSource(textBlock = """
            NaN, 0
            0, NaN
            NaN, NaN
        """)
        void rejects_nan(Double a, Double b) {
            assertThrows(IllegalArgumentException.class, () -> calc.add(a, b));
        }

        // Opcional: ative após endurecer validação no código (ver refatoração)
        @ParameterizedTest(name = "Infinity em add({0}, {1}) deve lançar IllegalArgumentException (após refatoração)")
        @CsvSource(textBlock = """
            Infinity, 1
            -Infinity, 1
            1, Infinity
            1, -Infinity
        """)
        void rejects_infinities(Double a, Double b) {
            assertThrows(IllegalArgumentException.class, () -> calc.add(a, b));
        }
    }
}
