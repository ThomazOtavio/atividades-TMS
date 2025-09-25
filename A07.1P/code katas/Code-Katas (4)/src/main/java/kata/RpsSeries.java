package kata;

/** Manages a best-of-N series for Rock–Paper–Scissors. */
public final class RpsSeries {

    private final int targetWins;
    private int p1Wins = 0;
    private int p2Wins = 0;
    private boolean over = false;

    /** Creates a best-of-n series. n must be odd and >= 1. */
    public RpsSeries(int bestOf) {
        if (bestOf < 1 || bestOf % 2 == 0) {
            throw new IllegalArgumentException("bestOf must be an odd positive number (e.g., 1, 3, 5): " + bestOf);
        }
        this.targetWins = (bestOf / 2) + 1;
    }

    /** Records one round and updates score. Draws don't count toward wins. */
    public void record(RockPaperScissors.Move p1, RockPaperScissors.Move p2) {
        if (over) throw new IllegalStateException("Series already over");
        RockPaperScissors.Outcome o = RockPaperScissors.play(p1, p2);
        switch (o) {
            case PLAYER1_WIN -> p1Wins++;
            case PLAYER2_WIN -> p2Wins++;
            case DRAW -> {} // no change
        }
        if (p1Wins >= targetWins || p2Wins >= targetWins) {
            over = true;
        }
    }

    public int getP1Wins() { return p1Wins; }
    public int getP2Wins() { return p2Wins; }
    public boolean isOver() { return over; }
    public Integer winner() {
        if (!over) return null;
        return (p1Wins > p2Wins) ? 1 : 2;
    }
}
