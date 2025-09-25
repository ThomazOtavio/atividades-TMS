package com.teixeira.tdd.mines;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperTest {

    @Test
    @DisplayName("Exemplo do enunciado 4x3")
    void example() {
        List<String> input = List.of(
                "*...",
                "..*.",
                "...."
        );
        List<String> expected = List.of(
                "*211",
                "12*1",
                "0111"
        );
        assertEquals(expected, new Minesweeper().annotate(input));
    }

    @Test
    @DisplayName("Campo vazio: retorna lista vazia")
    void emptyField() {
        assertEquals(List.of(), new Minesweeper().annotate(List.of()));
    }

    @Test
    @DisplayName("Uma única mina")
    void singleMine() {
        List<String> input = List.of("*");
        List<String> expected = List.of("*");
        assertEquals(expected, new Minesweeper().annotate(input));
    }

    @Test
    @DisplayName("Sem minas: todos zeros")
    void noMines() {
        List<String> input = List.of("...", "...");
        List<String> expected = List.of("000", "000");
        assertEquals(expected, new Minesweeper().annotate(input));
    }

    @Test
    @DisplayName("Valida caracteres inválidos e linhas irregulares")
    void validatesInput() {
        assertThrows(IllegalArgumentException.class,
                () -> new Minesweeper().annotate(List.of("..a", "...")));
        assertThrows(IllegalArgumentException.class,
                () -> new Minesweeper().annotate(List.of("..", "...")));
    }
}
