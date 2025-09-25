package com.teixeira.tdd.roman;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RomanNumeralConverterTest {

    private final RomanNumeralConverter conv = new RomanNumeralConverter();

    // ======= toRoman =======

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "1, I",
            "3, III",
            "4, IV",
            "5, V",
            "9, IX",
            "10, X",
            "14, XIV",
            "19, XIX",
            "40, XL",
            "44, XLIV",
            "58, LVIII",
            "90, XC",
            "94, XCIV",
            "400, CD",
            "944, CMXLIV",
            "1994, MCMXCIV",
            "2024, MMXXIV",
            "3999, MMMCMXCIX"
    })
    void arabicToRoman(int n, String expected) {
        assertEquals(expected, conv.toRoman(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 4000, 5000})
    @DisplayName("toRoman fora do intervalo [1,3999] lança OutOfRangeException")
    void toRomanOutOfRange(int n) {
        assertThrows(OutOfRangeException.class, () -> conv.toRoman(n));
    }

    // ======= toArabic =======

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "I, 1",
            "III, 3",
            "IV, 4",
            "V, 5",
            "IX, 9",
            "X, 10",
            "XIV, 14",
            "XIX, 19",
            "XL, 40",
            "XLIV, 44",
            "LVIII, 58",
            "XC, 90",
            "XCIV, 94",
            "CD, 400",
            "CMXLIV, 944",
            "MCMXCIV, 1994",
            "mmxxiv, 2024", // aceita minúsculas
            "MMMCMXCIX, 3999"
    })
    void romanToArabic(String roman, int expected) {
        assertEquals(expected, conv.toArabic(roman));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "A", "IIII", "VV", "IIV", "VX", "IL", "IC", "XM", "XLAC", "MMXXIIV"})
    @DisplayName("Romanos inválidos devem lançar InvalidRomanNumeralException")
    void invalidRoman(String roman) {
        assertThrows(InvalidRomanNumeralException.class, () -> conv.toArabic(roman));
    }

    @ParameterizedTest
    @ValueSource(strings = {"MMMM"})
    @DisplayName("Romano representando >3999 deve lançar OutOfRangeException")
    void romanOutOfRange(String roman) {
        assertThrows(OutOfRangeException.class, () -> conv.toArabic(roman));
    }

    @Test
    @DisplayName("Propriedade de ida-e-volta para alguns valores")
    void roundTrip() {
        int[] samples = {1, 4, 9, 58, 94, 2024, 3999};
        for (int n : samples) {
            String r = conv.toRoman(n);
            int back = conv.toArabic(r);
            assertEquals(n, back, () -> n + " -> " + r + " -> " + back);
        }
    }
}
