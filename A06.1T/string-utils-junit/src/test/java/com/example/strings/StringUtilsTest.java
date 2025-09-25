package com.example.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StringUtils tests")
class StringUtilsTest {

    // --- reverse ------------------------------------------------------------
    @Nested
    class ReverseTests {

        @Test
        void reverse_basic() {
            assertEquals("cba", StringUtils.reverse("abc"));
        }

        @Test
        void reverse_empty() {
            assertEquals("", StringUtils.reverse(""));
        }

        @Test
        void reverse_unicode_preserves_emoji() {
            String s = "A😀B"; // emoji is a surrogate pair
            assertEquals("B😀A", StringUtils.reverse(s));
        }

        @Test
        void reverse_accents_and_specials() {
            assertEquals("!çá", StringUtils.reverse("áç!"));
        }

        @Test
        void reverse_long_string() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10000; i++) sb.append('a');
            String longA = sb.toString();
            assertEquals(longA, StringUtils.reverse(longA));
        }

        @Test
        void reverse_null_throws() {
            assertThrows(IllegalArgumentException.class, () -> StringUtils.reverse(null));
        }
    }

    // --- countOccurrences (char) -------------------------------------------
    @Nested
    class CountCharTests {

        @ParameterizedTest(name = "countOccurrences("{0}", '{1}') = {2}")
        @CsvSource({
                "'banana', 'a', 3",
                "'banana', 'b', 1",
                "'BANANA', 'a', 0",
                "'', 'x', 0",
                "'ççá', 'ç', 2"
        })
        void count_char_various(String s, char ch, long expected) {
            assertEquals(expected, StringUtils.countOccurrences(s, ch));
        }

        @Test
        void count_char_null_throws() {
            assertThrows(IllegalArgumentException.class, () -> StringUtils.countOccurrences(null, 'a'));
        }
    }

    // --- countOccurrencesCodePoint (emoji etc.) ----------------------------
    @Nested
    class CountCodePointTests {

        @Test
        void count_emoji() {
            String s = "😀a😀😀";
            int CP = 0x1F600; // 😀
            assertEquals(3, StringUtils.countOccurrencesCodePoint(s, CP));
        }

        @Test
        void count_code_point_null_throws() {
            assertThrows(IllegalArgumentException.class,
                    () -> StringUtils.countOccurrencesCodePoint(null, 0x1F600));
        }
    }

    // --- isPalindrome -------------------------------------------------------
    @Nested
    class PalindromeTests {

        @ValueSource(strings = {"", "a", "aa", "aba", "A man, a plan, a canal: Panama!", "12321", "No 'x' in Nixon"})
        @ParameterizedTest(name = "isPalindrome("{0}") = true")
        void palindromes_true(String s) {
            assertTrue(StringUtils.isPalindrome(s));
        }

        @ValueSource(strings = {"ab", "abc", "hello", "Not a palindrome"})
        @ParameterizedTest(name = "isPalindrome("{0}") = false")
        void palindromes_false(String s) {
            assertFalse(StringUtils.isPalindrome(s));
        }

        @Test
        void palindrome_with_accents_and_mixed_case() {
            assertTrue(StringUtils.isPalindrome("Ábà, aBá.")); // normalized by lower-casing and stripping non-alnum
        }

        @Test
        void palindrome_null_throws() {
            assertThrows(IllegalArgumentException.class, () -> StringUtils.isPalindrome(null));
        }
    }

    // --- toUpper / toLower --------------------------------------------------
    @Nested
    class CaseConversionTests {

        @Test
        void to_upper_basic() {
            assertEquals("ABC", StringUtils.toUpper("abc"));
        }

        @Test
        void to_lower_basic() {
            assertEquals("abc", StringUtils.toLower("ABC"));
        }

        @Test
        void to_upper_handles_german_sz() {
            assertEquals("STRASSE", StringUtils.toUpper("straße"));
        }

        @Test
        void to_lower_handles_accents() {
            assertEquals("ação", StringUtils.toLower("AÇÃO"));
        }

        @Test
        void case_conversion_empty_ok() {
            assertEquals("", StringUtils.toUpper(""));
            assertEquals("", StringUtils.toLower(""));
        }

        @Test
        void case_conversion_null_throws() {
            assertThrows(IllegalArgumentException.class, () -> StringUtils.toUpper(null));
            assertThrows(IllegalArgumentException.class, () -> StringUtils.toLower(null));
        }
    }
}
