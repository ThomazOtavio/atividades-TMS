package kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class FizzBuzzTest {

    @ParameterizedTest
    @CsvSource({
        "1,1",
        "2,2",
        "3,Fizz",
        "4,4",
        "5,Buzz",
        "6,Fizz",
        "9,Fizz",
        "10,Buzz",
        "12,Fizz",
        "15,FizzBuzz",
        "30,FizzBuzz",
        "45,FizzBuzz",
        "98,98",
        "99,Fizz",
        "100,Buzz"
    })
    void singleValueCases(int n, String expected) {
        assertEquals(expected, FizzBuzz.of(n));
    }

    @Test
    void fullSequence1to100_hasExpectedLengthAndBoundaries() {
        List<String> seq = FizzBuzz.generate(1, 100);
        assertEquals(100, seq.size());
        assertEquals("1", seq.get(0));
        assertEquals("Buzz", seq.get(99));
    }

    @Test
    void ruleHoldsForRangePropertyCheck() {
        // Property: for any n, output respects divisibility rules
        for (int n = 1; n <= 1000; n++) {
            String s = FizzBuzz.of(n);
            if (n % 15 == 0) assertEquals("FizzBuzz", s);
            else if (n % 3 == 0) assertEquals("Fizz", s);
            else if (n % 5 == 0) assertEquals("Buzz", s);
            else assertEquals(Integer.toString(n), s);
        }
    }

    @Test
    void generateValidatesRange() {
        assertThrows(IllegalArgumentException.class, () -> FizzBuzz.generate(10, 1));
    }
}
