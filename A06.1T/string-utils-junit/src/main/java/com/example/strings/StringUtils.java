package com.example.strings;

import java.util.Locale;

/**
 * String utility operations:
 *  - reverse
 *  - countOccurrences (char-based)
 *  - countOccurrencesCodePoint (code point-based, e.g., for emoji)
 *  - isPalindrome (ignores non-alphanumerics and case)
 *  - toUpper / toLower (Locale.ROOT to avoid locale surprises)
 *
 * All methods throw IllegalArgumentException on null input.
 */
public class StringUtils {

    private static void requireNonNull(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }
    }

    public static String reverse(String s) {
        requireNonNull(s);
        // Reverse by code points to keep surrogate pairs intact
        int[] cps = s.codePoints().toArray();
        StringBuilder sb = new StringBuilder(cps.length);
        for (int i = cps.length - 1; i >= 0; i--) {
            sb.appendCodePoint(cps[i]);
        }
        return sb.toString();
    }

    public static long countOccurrences(String s, char ch) {
        requireNonNull(s);
        long count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ch) count++;
        }
        return count;
    }

    public static long countOccurrencesCodePoint(String s, int codePoint) {
        requireNonNull(s);
        return s.codePoints().filter(cp -> cp == codePoint).count();
    }

    /**
     * Palindrome check that ignores non-alphanumeric and case,
     * and works on Unicode code points.
     * Empty string is considered a palindrome.
     */
    public static boolean isPalindrome(String s) {
        requireNonNull(s);
        int[] cps = s.codePoints()
                .filter(cp -> Character.isLetterOrDigit(cp))
                .map(Character::toLowerCase)
                .toArray();
        int i = 0, j = cps.length - 1;
        while (i < j) {
            if (cps[i] != cps[j]) return false;
            i++; j--;
        }
        return true;
    }

    public static String toUpper(String s) {
        requireNonNull(s);
        return s.toUpperCase(Locale.ROOT);
    }

    public static String toLower(String s) {
        requireNonNull(s);
        return s.toLowerCase(Locale.ROOT);
    }
}
