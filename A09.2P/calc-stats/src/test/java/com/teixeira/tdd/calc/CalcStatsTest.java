package com.teixeira.tdd.calc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalcStatsTest {

    @Test
    @DisplayName("Exemplo do enunciado")
    void example() {
        int[] arr = {6, 9, 15, -2, 92, 11};
        var s = CalcStats.analyze(arr);
        assertEquals(-2, s.min());
        assertEquals(92, s.max());
        assertEquals(6, s.count());
        assertEquals(18.166666666666668, s.average()); // double exato de 109/6
    }

    @Test
    @DisplayName("Sequência unitária")
    void singleElement() {
        var s = CalcStats.analyze(new int[]{42});
        assertEquals(42, s.min());
        assertEquals(42, s.max());
        assertEquals(1, s.count());
        assertEquals(42.0, s.average());
    }

    @Test
    @DisplayName("Sequência negativa")
    void negatives() {
        var s = CalcStats.analyze(new int[]{-5, -1, -3});
        assertEquals(-5, s.min());
        assertEquals(-1, s.max());
        assertEquals(3, s.count());
        assertEquals(-3.0, s.average());
    }

    @Test
    @DisplayName("Nulo ou vazio devem falhar")
    void nullOrEmpty() {
        assertThrows(NullPointerException.class, () -> CalcStats.analyze(null));
        assertThrows(IllegalArgumentException.class, () -> CalcStats.analyze(new int[]{}));
    }
}
