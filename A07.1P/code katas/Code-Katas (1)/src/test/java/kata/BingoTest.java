package kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BingoTest {

    @Test
    void generatedCardRespectsColumnRangesAndUniqueness() {
        Random rnd = new Random(42);
        BingoCard card = BingoCard.generate(rnd);
        for (int c = 0; c < BingoCard.SIZE; c++) {
            Set<Integer> seen = new HashSet<>();
            int min = 1 + 15 * c;
            int max = 15 + 15 * c;
            for (int r = 0; r < BingoCard.SIZE; r++) {
                if (r == 2 && c == 2) continue; // center is free
                int n = card.getNumber(r, c);
                assertTrue(n >= min && n <= max, "col " + c + " value out of range: " + n);
                assertTrue(seen.add(n), "duplicate in column " + c + ": " + n);
            }
        }
        assertEquals(0, card.getNumber(2, 2));
        assertTrue(card.isMarked(2,2)); // center marked
    }

    @Test
    void callerYieldsAllNumbersOnce() {
        Random rnd = new Random(123);
        BingoCaller caller = new BingoCaller(rnd);
        Set<Integer> seen = new HashSet<>();
        Integer n;
        int count = 0;
        while ((n = caller.next()) != null) {
            assertTrue(n >= 1 && n <= 75);
            assertTrue(seen.add(n), "duplicate number called: " + n);
            count++;
        }
        assertEquals(75, count);
        assertEquals(0, caller.remaining());
    }

    @Test
    void markingAndBingoDetectionWorksForRowsColsDiagonals() {
        Random rnd = new Random(7);
        BingoCard card = BingoCard.generate(rnd);

        // Force a row bingo: mark all numbers in row 0
        for (int c = 0; c < BingoCard.SIZE; c++) {
            if (c == 2 && 0 == 2) continue; // center check doesn't apply here
            int val = card.getNumber(0, c);
            card.mark(val);
        }
        assertTrue(card.hasBingo());

        // New card to test a column bingo
        card = BingoCard.generate(rnd);
        for (int r = 0; r < BingoCard.SIZE; r++) {
            if (r == 2 && 2 == 2) continue;
            int val = card.getNumber(r, 0);
            card.mark(val);
        }
        assertTrue(card.hasBingo());

        // New card to test a diagonal bingo (top-left to bottom-right)
        card = BingoCard.generate(rnd);
        for (int i = 0; i < BingoCard.SIZE; i++) {
            if (i == 2) continue; // center already marked
            int val = card.getNumber(i, i);
            card.mark(val);
        }
        assertTrue(card.hasBingo());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 76, -5})
    void markingRejectsOutOfRangeNumbers(int bad) {
        Random rnd = new Random(1);
        BingoCard card = BingoCard.generate(rnd);
        assertThrows(IllegalArgumentException.class, () -> card.mark(bad));
    }

    @Test
    void fullGameReachesBingoEventually() {
        BingoGame game = new BingoGame(99);
        int draws = game.playToBingo();
        assertTrue(draws >= 1 && draws <= 75);
        assertTrue(game.getCard().hasBingo());
    }
}
