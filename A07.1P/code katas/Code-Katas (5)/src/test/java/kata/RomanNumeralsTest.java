package kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class RomanNumeralsTest {

    @ParameterizedTest
    @CsvSource({
        "1,I",
        "2,II",
        "3,III",
        "4,IV",
        "5,V",
        "6,VI",
        "7,VII",
        "8,VIII",
        "9,IX",
        "10,X",
        "14,XIV",
        "19,XIX",
        "21,XXI",
        "39,XXXIX",
        "40,XL",
        "44,XLIV",
        "49,XLIX",
        "50,L",
        "90,XC",
        "99,XCIX",
        "100,C",
        "400,CD",
        "444,CDXLIV",
        "500,D",
        "900,CM",
        "944,CMXLIV",
        "1000,M",
        "1984,MCMLXXXIV",
        "2421,MMCDXXI",
        "3888,MMMDCCCLXXXVIII",
        "3999,MMMCMXCIX"
    })
    void convertsArabicToRoman(int arabic, String roman) {
        assertEquals(roman, RomanNumerals.toRoman(arabic));
    }

    @ParameterizedTest
    @CsvSource({
        "I,1",
        "II,2",
        "III,3",
        "IV,4",
        "V,5",
        "VI,6",
        "VII,7",
        "VIII,8",
        "IX,9",
        "X,10",
        "XIV,14",
        "XIX,19",
        "XXI,21",
        "XXXIX,39",
        "XL,40",
        "XLIV,44",
        "XLIX,49",
        "L,50",
        "XC,90",
        "XCIX,99",
        "C,100",
        "CD,400",
        "CDXLIV,444",
        "D,500",
        "CM,900",
        "CMXLIV,944",
        "M,1000",
        "MCMLXXXIV,1984",
        "MMCDXXI,2421",
        "MMMDCCCLXXXVIII,3888",
        "MMMCMXCIX,3999"
    })
    void parsesRomanToArabic(String roman, int arabic) {
        assertEquals(arabic, RomanNumerals.fromRoman(roman));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 4000, 50000})
    void rejectsOutOfRangeArabicNumbers(int n) {
        assertThrows(IllegalArgumentException.class, () -> RomanNumerals.toRoman(n));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "IIII", "VX", "IC", "IL", "XM", "MCMC", "ABC", "IM"})
    void rejectsInvalidRomanNumerals(String s) {
        assertThrows(IllegalArgumentException.class, () -> RomanNumerals.fromRoman(s));
    }

    @Test
    void canonicalFormRoundTrip() {
        for (int i = 1; i <= 3999; i++) {
            String roman = RomanNumerals.toRoman(i);
            assertEquals(i, RomanNumerals.fromRoman(roman));
        }
    }
}
