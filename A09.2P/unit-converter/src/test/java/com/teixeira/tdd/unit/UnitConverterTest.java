package com.teixeira.tdd.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    private final UnitConverter c = new UnitConverter();
    private static final double EPS = 1e-9;

    // ===== Length =====
    @ParameterizedTest(name = "{0} {1} -> {2} {3}")
    @CsvSource({
            "1, km, 1000, m",
            "1, m, 100, cm",
            "2.54, cm, 1, in",
            "10, mi, 16.09344, km",
            "1, m, 39.37007874015748, in"
    })
    void lengthConversions(double value, String from, double expected, String to) {
        assertEquals(expected, c.convertLength(value, from, to), EPS);
    }

    @Test
    @DisplayName("Unidade de comprimento inválida")
    void invalidLengthUnit() {
        assertThrows(InvalidUnitException.class, () -> c.convertLength(1, "meter", "km"));
        assertThrows(InvalidUnitException.class, () -> c.convertLength(1, "m", "kms"));
    }

    // ===== Mass =====
    @ParameterizedTest(name = "{0} {1} -> {2} {3}")
    @CsvSource({
            "1, kg, 1000, g",
            "1000, g, 1, kg",
            "1, lb, 0.45359237, kg",
            "16, oz, 1, lb",
            "1, oz, 28.349523125, g"
    })
    void massConversions(double value, String from, double expected, String to) {
        assertEquals(expected, c.convertMass(value, from, to), EPS);
    }

    @Test
    @DisplayName("Unidade de massa inválida")
    void invalidMassUnit() {
        assertThrows(InvalidUnitException.class, () -> c.convertMass(1, "gram", "kg"));
        assertThrows(InvalidUnitException.class, () -> c.convertMass(1, "g", "kgs"));
    }

    // ===== Temperature =====
    @ParameterizedTest(name = "{0}°{1} -> {2}°{3}")
    @CsvSource({
            "0, C, 32, F",
            "100, C, 212, F",
            "25, C, 298.15, K",
            "273.15, K, 0, C",
            "32, F, 0, C",
            "212, F, 100, C",
            "77, F, 25, C",
            "77, F, 298.15, K"
    })
    void temperatureConversions(double value, String from, double expected, String to) {
        assertEquals(expected, c.convertTemperature(value, from, to), 1e-9);
    }

    @Test
    @DisplayName("Unidade de temperatura inválida")
    void invalidTemperatureUnit() {
        assertThrows(InvalidUnitException.class, () -> c.convertTemperature(0, "cel", "K"));
        assertThrows(InvalidUnitException.class, () -> c.convertTemperature(0, "C", "kel"));
    }

    // ===== Sanity: same unit returns same value =====
    @Test
    void identityConversions() {
        assertEquals(42.0, c.convertLength(42.0, "m", "m"), EPS);
        assertEquals(5.5, c.convertMass(5.5, "kg", "kg"), EPS);
        assertEquals(-10.0, c.convertTemperature(-10.0, "C", "C"), EPS);
    }
}
