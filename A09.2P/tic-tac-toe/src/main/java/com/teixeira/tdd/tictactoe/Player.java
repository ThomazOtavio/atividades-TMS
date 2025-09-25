package com.teixeira.tdd.tictactoe;

public enum Player {
    X, O;

    public Player other() {
        return this == X ? O : X;
    }
}
