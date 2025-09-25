package com.teixeira.tdd.tictactoe;

import java.util.Arrays;

public class TicTacToe {

    private final Player[][] board = new Player[3][3];
    private Player current = Player.X;
    private GameState state = GameState.IN_PROGRESS;

    public Player getCurrentPlayer() { return current; }
    public GameState getState() { return state; }

    /** Retorna o conteúdo da célula (ou null para vazia). */
    public Player getCell(int row, int col) {
        checkBounds(row, col);
        return board[row][col];
    }

    /** Executa uma jogada na célula indicada. */
    public void move(int row, int col) {
        if (state != GameState.IN_PROGRESS) {
            throw new InvalidMoveException("game is over: " + state);
        }
        checkBounds(row, col);
        if (board[row][col] != null) {
            throw new InvalidMoveException("cell already occupied");
        }

        board[row][col] = current;

        if (hasWon(current)) {
            state = current == Player.X ? GameState.X_WON : GameState.O_WON;
            return;
        }
        if (isBoardFull()) {
            state = GameState.DRAW;
            return;
        }
        current = current.other();
    }

    private void checkBounds(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new InvalidMoveException("position out of bounds: (" + row + "," + col + ")");
        }
    }

    private boolean hasWon(Player p) {
        // rows and cols
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true;
            if (board[0][i] == p && board[1][i] == p && board[2][i] == p) return true;
        }
        // diagonals
        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) return true;
        if (board[2][0] == p && board[1][1] == p && board[0][2] == p) return true;
        return false;
    }

    private boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == null) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                sb.append(board[r][c] == null ? "." : board[r][c]);
                if (c < 2) sb.append("|");
            }
            if (r < 2) sb.append("\n-----\n");
        }
        return sb.toString();
    }
}
