package kata;

public final class RockPaperScissors {

    public enum Move { ROCK, PAPER, SCISSORS }
    public enum Outcome { PLAYER1_WIN, PLAYER2_WIN, DRAW }

    private RockPaperScissors() {}

    /** Parses a move from a string, case-insensitive. Accepts: rock, paper, scissors. */
    public static Move parse(String s) {
        if (s == null) throw new IllegalArgumentException("Move cannot be null");
        return switch (s.trim().toLowerCase()) {
            case "rock", "r" -> Move.ROCK;
            case "paper", "p" -> Move.PAPER;
            case "scissors", "scissor", "s" -> Move.SCISSORS;
            default -> throw new IllegalArgumentException("Unknown move: " + s);
        };
    }

    /** Determines the outcome for player1 vs player2. */
    public static Outcome play(Move p1, Move p2) {
        if (p1 == null || p2 == null) throw new IllegalArgumentException("Moves cannot be null");
        if (p1 == p2) return Outcome.DRAW;

        return switch (p1) {
            case ROCK -> (p2 == Move.SCISSORS) ? Outcome.PLAYER1_WIN : Outcome.PLAYER2_WIN;
            case PAPER -> (p2 == Move.ROCK) ? Outcome.PLAYER1_WIN : Outcome.PLAYER2_WIN;
            case SCISSORS -> (p2 == Move.PAPER) ? Outcome.PLAYER1_WIN : Outcome.PLAYER2_WIN;
        };
    }

    /** Small demo */
    public static void main(String[] args) {
        var p1 = parse(args.length > 0 ? args[0] : "rock");
        var p2 = parse(args.length > 1 ? args[1] : "scissors");
        Outcome outcome = play(p1, p2);
        System.out.println("P1=" + p1 + " vs P2=" + p2 + " -> " + outcome);
    }
}
