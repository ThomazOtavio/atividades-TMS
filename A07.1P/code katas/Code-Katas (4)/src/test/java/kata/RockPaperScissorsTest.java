package kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class RockPaperScissorsTest {

    @ParameterizedTest
    @CsvSource({
        "ROCK,ROCK,DRAW",
        "ROCK,PAPER,PLAYER2_WIN",
        "ROCK,SCISSORS,PLAYER1_WIN",
        "PAPER,ROCK,PLAYER1_WIN",
        "PAPER,PAPER,DRAW",
        "PAPER,SCISSORS,PLAYER2_WIN",
        "SCISSORS,ROCK,PLAYER2_WIN",
        "SCISSORS,PAPER,PLAYER1_WIN",
        "SCISSORS,SCISSORS,DRAW"
    })
    void allPairingsBehaveAsExpected(RockPaperScissors.Move p1, RockPaperScissors.Move p2, RockPaperScissors.Outcome expected) {
        assertEquals(expected, RockPaperScissors.play(p1, p2));
    }

    @ParameterizedTest
    @CsvSource({
        "rock,ROCK",
        "Rock,ROCK",
        "r,ROCK",
        "paper,PAPER",
        "P, PAPER",
        "scissors,SCISSORS",
        "Scissor,SCISSORS",
        "s,SCISSORS"
    })
    void parseIsCaseInsensitiveAndAcceptsShortcuts(String input, RockPaperScissors.Move expected) {
        assertEquals(expected, RockPaperScissors.parse(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "lizard", "spock", "123"})
    void parseRejectsUnknownMoves(String bad) {
        assertThrows(IllegalArgumentException.class, () -> RockPaperScissors.parse(bad));
    }

    @Test
    void seriesBestOf3EndsWhenPlayerReaches2Wins() {
        RpsSeries series = new RpsSeries(3);
        assertFalse(series.isOver());
        series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.SCISSORS); // P1 win (1-0)
        assertEquals(1, series.getP1Wins());
        assertEquals(0, series.getP2Wins());
        series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.PAPER); // P2 win (1-1)
        assertFalse(series.isOver());
        series.record(RockPaperScissors.Move.SCISSORS, RockPaperScissors.Move.PAPER); // P1 win (2-1) -> over
        assertTrue(series.isOver());
        assertEquals(1, series.winner());
        assertThrows(IllegalStateException.class, () -> series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.ROCK));
    }

    @Test
    void seriesBestOf5AllowsDrawsWithoutProgress() {
        RpsSeries series = new RpsSeries(5);
        series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.ROCK); // draw
        assertFalse(series.isOver());
        assertEquals(0, series.getP1Wins());
        assertEquals(0, series.getP2Wins());
        // play to completion
        series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.SCISSORS); // P1
        series.record(RockPaperScissors.Move.PAPER, RockPaperScissors.Move.SCISSORS); // P2
        series.record(RockPaperScissors.Move.SCISSORS, RockPaperScissors.Move.PAPER); // P1
        series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.PAPER); // P2
        assertFalse(series.isOver());
        series.record(RockPaperScissors.Move.ROCK, RockPaperScissors.Move.SCISSORS); // P1 wins 3rd
        assertTrue(series.isOver());
        assertEquals(1, series.winner());
    }

    @Test
    void bestOfMustBeOddAndPositive() {
        assertThrows(IllegalArgumentException.class, () -> new RpsSeries(0));
        assertThrows(IllegalArgumentException.class, () -> new RpsSeries(2));
        assertDoesNotThrow(() -> new RpsSeries(1));
        assertDoesNotThrow(() -> new RpsSeries(3));
    }
}
