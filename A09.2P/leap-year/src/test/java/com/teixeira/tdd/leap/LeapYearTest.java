package com.teixeira.tdd.leap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LeapYearTest {

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "1996, true",
            "2001, false",
            "1900, false",
            "2000, true",
            "2400, true",
            "2100, false",
            "2024, true",
            "2023, false"
    })
    @DisplayName("Casos clássicos de ano bissexto")
    void leapCases(int year, boolean expected) {
        assertEquals(expected, LeapYear.isLeap(year));
    }
}
