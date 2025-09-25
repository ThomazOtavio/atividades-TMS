package kata;

import java.util.Map;
import java.util.HashMap;

public final class RomanNumerals {

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] NUMERALS = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    private static final Map<Character, Integer> CHAR_VALUES = new HashMap<>();
    static {
        CHAR_VALUES.put('I', 1);
        CHAR_VALUES.put('V', 5);
        CHAR_VALUES.put('X', 10);
        CHAR_VALUES.put('L', 50);
        CHAR_VALUES.put('C', 100);
        CHAR_VALUES.put('D', 500);
        CHAR_VALUES.put('M', 1000);
    }

    private RomanNumerals() {}

    /** Converts an integer in the range [1, 3999] to a Roman numeral in standard form. */
    public static String toRoman(int number) {
        if (number <= 0 || number >= 4000) {
            throw new IllegalArgumentException("Number out of range (must be 1..3999): " + number);
        }
        StringBuilder sb = new StringBuilder();
        int n = number;
        for (int i = 0; i < VALUES.length; i++) {
            while (n >= VALUES[i]) {
                n -= VALUES[i];
                sb.append(NUMERALS[i]);
            }
        }
        return sb.toString();
    }

    /** Parses a Roman numeral and returns its integer value, validating standard notation. */
    public static int fromRoman(String roman) {
        if (roman == null || roman.isBlank()) {
            throw new IllegalArgumentException("Roman numeral must be non-empty");
        }
        String s = roman.trim().toUpperCase();

        // Validate characters
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!CHAR_VALUES.containsKey(c)) {
                throw new IllegalArgumentException("Invalid character in Roman numeral: " + c);
            }
        }

        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int value = CHAR_VALUES.get(s.charAt(i));
            if (i + 1 < s.length()) {
                int next = CHAR_VALUES.get(s.charAt(i + 1));
                if (value < next) {
                    total += (next - value);
                    i++; // skip next
                    continue;
                }
            }
            total += value;
        }

        // Range check and canonical form validation
        if (total <= 0 || total >= 4000) {
            throw new IllegalArgumentException("Roman numeral value out of range (1..3999): " + roman);
        }
        // Ensure the input is the canonical (standard) representation
        String canonical = toRoman(total);
        if (!canonical.equals(s)) {
            throw new IllegalArgumentException("Non-standard or invalid Roman numeral: " + roman + " (canonical: " + canonical + ")");
        }
        return total;
    }
}
