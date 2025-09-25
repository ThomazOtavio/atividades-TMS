package kata;

import java.util.Objects;

/** Berlin Clock representation for a time in HH:MM:SS (24h). */
public final class BerlinClock {

    private BerlinClock() {}

    /** Returns the 5 lines of the Berlin Clock for the given time string.
     * Format: HH:MM:SS (24h). Accepts "24:00:00" as a special case. */
    public static String display(String time) {
        int[] hms = parse(time);
        int h = hms[0], m = hms[1], s = hms[2];

        String seconds = (s % 2 == 0) ? "Y" : "O";
        String fiveHours = repeat('R', h / 5, 4) + repeat('O', 4 - (h / 5), 0);
        String oneHours  = repeat('R', h % 5, 4) + repeat('O', 4 - (h % 5), 0);

        StringBuilder fiveMinutes = new StringBuilder(11);
        int on5 = m / 5;
        for (int i = 1; i <= 11; i++) {
            if (i <= on5) {
                fiveMinutes.append((i % 3 == 0) ? 'R' : 'Y');
            } else {
                fiveMinutes.append('O');
            }
        }
        String oneMinutes = repeat('Y', m % 5, 4) + repeat('O', 4 - (m % 5), 0);

        return String.join(System.lineSeparator(),
                seconds, fiveHours, oneHours, fiveMinutes.toString(), oneMinutes);
    }

    private static String repeat(char c, int count, int totalPadding) {
        if (count < 0) count = 0;
        StringBuilder sb = new StringBuilder(totalPadding > 0 ? totalPadding : count);
        for (int i = 0; i < count; i++) sb.append(c);
        return sb.toString();
    }

    /** Parses "HH:MM:SS" and returns int[3]={h,m,s}. Allows 24:00:00 only. */
    static int[] parse(String time) {
        Objects.requireNonNull(time, "time must not be null");
        String[] parts = time.trim().split(":");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid time format, expected HH:MM:SS");
        int h, m, s;
        try {
            h = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);
            s = Integer.parseInt(parts[2]);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid numeric values in time");
        }
        if (m < 0 || m > 59) throw new IllegalArgumentException("Minutes must be 0..59");
        if (s < 0 || s > 59) throw new IllegalArgumentException("Seconds must be 0..59");
        if (h == 24) {
            if (m != 0 || s != 0) throw new IllegalArgumentException("24 is only valid as 24:00:00");
        } else if (h < 0 || h > 23) {
            throw new IllegalArgumentException("Hours must be 0..23 (or 24:00:00)");
        }
        // For 24:00:00, treat hours as 24 to light 4+4 in the hours rows.
        return new int[]{h, m, s};
    }

    /** Simple CLI: prints the 5-line clock for a given HH:MM:SS. */
    public static void main(String[] args) {
        String t = (args.length > 0) ? args[0] : "13:17:01";
        System.out.println(display(t));
    }
}
