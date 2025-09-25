package com.teixeira.tdd.mines;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Minesweeper {

    /**
     * Recebe um campo como lista de strings (todas com mesmo comprimento)
     * contendo '*' para minas e '.' para vazios.
     * Retorna a lista com as dicas (mesma dimensão).
     */
    public List<String> annotate(List<String> field) {
        Objects.requireNonNull(field, "field");
        if (field.isEmpty()) return List.of();

        int rows = field.size();
        int cols = field.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            String line = field.get(r);
            if (line.length() != cols) throw new IllegalArgumentException("ragged field at row " + r);
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                if (ch != '*' && ch != '.') throw new IllegalArgumentException("invalid char '" + ch + "' at ("+r+","+c+")");
                grid[r][c] = ch;
            }
        }

        List<String> out = new ArrayList<>(rows);
        for (int r = 0; r < rows; r++) {
            StringBuilder sb = new StringBuilder(cols);
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '*') {
                    sb.append('*');
                } else {
                    int mines = countAdjacentMines(grid, r, c);
                    sb.append((char) ('0' + mines));
                }
            }
            out.add(sb.toString());
        }
        return out;
    }

    private static int countAdjacentMines(char[][] g, int r, int c) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr, nc = c + dc;
                if (nr >= 0 && nr < g.length && nc >= 0 && nc < g[0].length) {
                    if (g[nr][nc] == '*') count++;
                }
            }
        }
        return count;
    }
}
