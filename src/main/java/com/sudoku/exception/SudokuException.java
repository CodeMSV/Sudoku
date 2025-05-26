package com.sudoku.exception;

public class SudokuException extends RuntimeException{

    public SudokuException(String message) {
        super(message);
    }
    public SudokuException(String message, Throwable cause) {
        super(message, cause);
    }
}
