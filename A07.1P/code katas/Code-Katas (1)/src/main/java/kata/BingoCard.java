package kata;

import java.util.*;

/** Represents a standard US Bingo (75-ball) 5x5 card with a free center. */
public final class BingoCard {

    public static final int SIZE = 5;
    private final int[][] numbers = new int[SIZE][SIZE];
    private final boolean[][] marked = new boolean[SIZE][SIZE];

    private BingoCard() {}

    /** Generates a bingo card with column ranges:
     * B:1-15, I:16-30, N:31-45, G:46-60, O:61-75; center is free (marked). */
    public static BingoCard generate(Random rnd) {
        Objects.requireNonNull(rnd, "rnd");
        BingoCard card = new BingoCard();
        int base = 1;
        for (int col = 0; col < SIZE; col++) {
            int start = base + 15 * col;
            int end = start + 14;
            List<Integer> pool = new ArrayList<>();
            for (int n = start; n <= end; n++) pool.add(n);
            Collections.shuffle(pool, rnd);
            for (int row = 0; row < SIZE; row++) {
                card.numbers[row][col] = pool.get(row);
            }
        }
        // Free center
        card.numbers[SIZE/2][SIZE/2] = 0; // 0 represents FREE
        for (boolean[] row : card.marked) Arrays.fill(row, false);
        card.marked[SIZE/2][SIZE/2] = true;
        return card;
    }

    /** Marks the given number on the card if present. Returns true if any cell was marked. */
    public boolean mark(int n) {
        if (n < 1 || n > 75) throw new IllegalArgumentException("n must be 1..75");
        boolean changed = false;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (numbers[r][c] == n) {
                    marked[r][c] = true;
                    changed = true;
                }
            }
        }
        return changed;
    }

    /** Returns true if any row, column, or diagonal is fully marked (free center counts as marked). */
    public boolean hasBingo() {
        // Rows
        for (int r = 0; r < SIZE; r++) {
            boolean all = true;
            for (int c = 0; c < SIZE; c++) all &= marked[r][c];
            if (all) return true;
        }
        // Columns
        for (int c = 0; c < SIZE; c++) {
            boolean all = true;
            for (int r = 0; r < SIZE; r++) all &= marked[r][c];
            if (all) return true;
        }
        // Diagonals
        boolean d1 = true, d2 = true;
        for (int i = 0; i < SIZE; i++) {
            d1 &= marked[i][i];
            d2 &= marked[i][SIZE - 1 - i];
        }
        return d1 || d2;
    }

    public int getNumber(int row, int col) {
        return numbers[row][col];
    }
    public boolean isMarked(int row, int col) {
        return marked[row][col];
    }

    /** Returns a printable representation with B I N G O headers. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" B   I   N   G   O").append(System.lineSeparator());
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int n = numbers[r][c];
                if (r == SIZE/2 && c == SIZE/2) {
                    sb.append(" ** ");
                } else {
                    sb.append(String.format("%2d  ", n));
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
