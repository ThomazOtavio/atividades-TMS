package kata;

import java.util.ArrayList;
import java.util.List;

/** Classic FizzBuzz kata. */
public final class FizzBuzz {

    private FizzBuzz() {}

    /** Returns "Fizz" if n divisible by 3, "Buzz" if divisible by 5,
     * "FizzBuzz" if divisible by both, otherwise the number as a string. */
    public static String of(int n) {
        boolean fizz = n % 3 == 0;
        boolean buzz = n % 5 == 0;
        if (fizz && buzz) return "FizzBuzz";
        if (fizz) return "Fizz";
        if (buzz) return "Buzz";
        return Integer.toString(n);
    }

    /** Generates the FizzBuzz sequence from 'from' to 'to' inclusive. */
    public static List<String> generate(int from, int to) {
        if (to < from) throw new IllegalArgumentException("to must be >= from");
        List<String> out = new ArrayList<>(Math.max(0, to - from + 1));
        for (int i = from; i <= to; i++) {
            out.add(of(i));
        }
        return out;
    }

    /** Small CLI: prints 1..100 according to FizzBuzz rules. */
    public static void main(String[] args) {
        generate(1, 100).forEach(System.out::println);
    }
}
