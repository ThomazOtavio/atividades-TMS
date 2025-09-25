package com.teixeira.tdd.roman;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RomanNumeralConverter {
    private static final int MIN = 1;
    private static final int MAX = 3999;

    private static final int[]    VALUES  = {1000,900,500,400,100,90, 50,40,10, 9, 5, 4,1};
    private static final String[] SYMBOLS = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    private static final Map<Character, Integer> MAP = new HashMap<>();
    private static final Set<String> SUBTRACTIVES = Set.of("CM","CD","XC","XL","IX","IV");

    static {

        MAP.put('I', 1);
        MAP.put('V', 5);
        MAP.put('X', 10);
        MAP.put('L', 50);
        MAP.put('C', 100);
        MAP.put('D', 500);
        MAP.put('M', 1000);

    }

    /** Converte um inteiro (1..3999) para número romano canônico. */
    public String toRoman(int n) {
        if (n < MIN || n > MAX) throw new OutOfRangeException("value must be between 1 and 3999: " + n);
        StringBuilder sb = new StringBuilder();
        int remaining = n;
        for (int i = 0; i < VALUES.length; i++) {
            while (remaining >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                remaining -= VALUES[i];
            }
        }
        return sb.toString();
    }

    /** Converte um número romano válido para inteiro. Aceita minúsculas/maiúsculas. */
    public int toArabic(String roman) {
        if (roman == null || roman.trim().isEmpty())
            throw new InvalidRomanNumeralException("empty roman numeral");

        String s = roman.trim().toUpperCase(Locale.ROOT);

        // valida caracteres
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!MAP.containsKey(c)) {
                throw new InvalidRomanNumeralException("invalid character: '" + c + "'");
            }
        }

        int i = 0;
        int total = 0;
        while (i < s.length()) {
            if (i + 1 < s.length()) {
                String pair = s.substring(i, i + 2);
                if (SUBTRACTIVES.contains(pair)) {
                    total += value(pair);
                    i += 2;
                    continue;
                }
            }
            total += value(s.charAt(i));
            i++;
        }

        if (total < MIN || total > MAX) {
            throw new OutOfRangeException("roman value out of supported range 1..3999: " + s + " => " + total);
        }

        // valida forma canônica: re-gera e compara
        String canonical = toRoman(total);
        if (!canonical.equals(s)) {
            throw new InvalidRomanNumeralException("non-canonical or invalid roman numeral: " + roman);
        }
        return total;
    }

    private static int value(char c) {
        Integer v = MAP.get(c);
        if (v == null) throw new InvalidRomanNumeralException("invalid character: '" + c + "'");
        return v;
    }

    private static int value(String subtractive) {
        switch (subtractive) {
            case "CM": return 900;
            case "CD": return 400;
            case "XC": return 90;
            case "XL": return 40;
            case "IX": return 9;
            case "IV": return 4;
            default: throw new InvalidRomanNumeralException("invalid subtractive pair: " + subtractive);
        }
    }
}
