package kata;

import java.util.Objects;
import java.util.Random;

/** Simple one-card game loop helper, mainly for demo purposes. */
public final class BingoGame {
    private final BingoCard card;
    private final BingoCaller caller;

    public BingoGame(long seed) {
        Random rnd = new Random(seed);
        this.card = BingoCard.generate(rnd);
        this.caller = new BingoCaller(rnd);
    }

    public BingoCard getCard() { return card; }
    public BingoCaller getCaller() { return caller; }

    /** Plays until bingo, returns the draw count needed. */
    public int playToBingo() {
        int draws = 0;
        Integer n;
        while ((n = caller.next()) != null) {
            draws++;
            card.mark(n);
            if (card.hasBingo()) return draws;
        }
        return draws;
    }

    public static void main(String[] args) {
        long seed = (args.length > 0) ? Long.parseLong(args[0]) : System.currentTimeMillis();
        BingoGame game = new BingoGame(seed);
        System.out.println(game.getCard());
        int draws = game.playToBingo();
        System.out.println("Bingo in " + draws + " draws!");
    }
}
