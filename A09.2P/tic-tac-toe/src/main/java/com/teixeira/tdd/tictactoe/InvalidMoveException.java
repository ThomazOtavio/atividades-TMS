package com.teixeira.tdd.tictactoe;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) { super(message); }
}
