package kata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class BerlinClockTest {

    @ParameterizedTest
    @CsvSource(delimiterString = ";", value = {
        "00:00:00;Y;OOOO;OOOO;OOOOOOOOOOO;OOOO",
        "23:59:59;O;RRRR;RRRO;YYRYYRYYRYY;YYYY",
        "12:32:00;Y;RROO;RROO;YYRYYROOOOO;YYOO",
        "12:34:00;Y;RROO;RROO;YYRYYRYYOOO;YYYY",
        "12:35:00;Y;RROO;RROO;YYRYYRYOOOO;OOOO",
        "24:00:00;Y;RRRR;RRRR;OOOOOOOOOOO;OOOO"
    })
    void sampleTimes(String time, String seconds, String fiveH, String oneH, String fiveM, String oneM) {
        String[] lines = BerlinClock.display(time).split("\\R");
        assertEquals(5, lines.length);
        assertEquals(seconds, lines[0]);
        assertEquals(fiveH, lines[1]);
        assertEquals(oneH, lines[2]);
        assertEquals(fiveM, lines[3]);
        assertEquals(oneM, lines[4]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"25:00:00", "24:01:00", "12:60:00", "12:00:60", "ab:cd:ef", "12:3:4"})
    void rejectsInvalidTimes(String bad) {
        assertThrows(IllegalArgumentException.class, () -> BerlinClock.display(bad));
    }

    @Test
    void secondsLampEvenOdd() {
        assertEquals("Y", BerlinClock.display("01:00:00").split("\\R")[0]);
        assertEquals("O", BerlinClock.display("01:00:01").split("\\R")[0]);
    }
}
