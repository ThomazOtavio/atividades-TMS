package com.teixeira.tdd.tictactoe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

    @Test
    @DisplayName("Inicializa com tabuleiro vazio e jogador X começa")
    void initBoard() {
        TicTacToe g = new TicTacToe();
        assertEquals(Player.X, g.getCurrentPlayer());
        assertEquals(GameState.IN_PROGRESS, g.getState());
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertNull(g.getCell(r, c));
            }
        }
    }

    @Test
    @DisplayName("Alternância de jogadores a cada jogada válida")
    void alternatesPlayers() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        assertEquals(Player.O, g.getCurrentPlayer());
        g.move(1,1); // O
        assertEquals(Player.X, g.getCurrentPlayer());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1,0", "0,-1", "3,0", "0,3", "3,3", "-1,-1"})
    @DisplayName("Rejeita jogadas fora do tabuleiro")
    void rejectsOutOfBounds(String coord) {
        String[] parts = coord.split(",");
        int r = Integer.parseInt(parts[0].trim());
        int c = Integer.parseInt(parts[1].trim());
        TicTacToe g = new TicTacToe();
        assertThrows(InvalidMoveException.class, () -> g.move(r, c));
    }

    @Test
    @DisplayName("Rejeita jogar em célula ocupada")
    void rejectsOccupied() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        assertThrows(InvalidMoveException.class, () -> g.move(0,0)); // O tenta mesma
    }

    @Test
    @DisplayName("Vitória em linha")
    void winRow() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        g.move(1,0); // O
        g.move(0,1); // X
        g.move(1,1); // O
        g.move(0,2); // X -> vence
        assertEquals(GameState.X_WON, g.getState());
    }

    @Test
    @DisplayName("Vitória em coluna")
    void winColumn() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        g.move(0,1); // O
        g.move(1,0); // X
        g.move(1,1); // O
        g.move(2,0); // X -> vence
        assertEquals(GameState.X_WON, g.getState());
    }

    @Test
    @DisplayName("Vitória na diagonal principal")
    void winMainDiagonal() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        g.move(0,1); // O
        g.move(1,1); // X
        g.move(0,2); // O
        g.move(2,2); // X -> vence
        assertEquals(GameState.X_WON, g.getState());
    }

    @Test
    @DisplayName("Vitória na diagonal secundária")
    void winAntiDiagonal() {
        TicTacToe g = new TicTacToe();
        g.move(2,0); // X
        g.move(0,0); // O
        g.move(1,1); // X
        g.move(0,1); // O
        g.move(0,2); // X -> vence
        assertEquals(GameState.X_WON, g.getState());
    }

    @Test
    @DisplayName("Empate quando o tabuleiro enche sem vencedor")
    void drawGame() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        g.move(0,1); // O
        g.move(0,2); // X
        g.move(1,1); // O
        g.move(1,0); // X
        g.move(1,2); // O
        g.move(2,1); // X
        g.move(2,0); // O
        g.move(2,2); // X
        assertEquals(GameState.DRAW, g.getState());
    }

    @Test
    @DisplayName("Não permite jogadas após vitória ou empate")
    void noMovesAfterGameOver() {
        TicTacToe g = new TicTacToe();
        g.move(0,0); // X
        g.move(1,0); // O
        g.move(0,1); // X
        g.move(1,1); // O
        g.move(0,2); // X -> X venceu
        assertEquals(GameState.X_WON, g.getState());
        assertThrows(InvalidMoveException.class, () -> g.move(2,2));
    }
}
