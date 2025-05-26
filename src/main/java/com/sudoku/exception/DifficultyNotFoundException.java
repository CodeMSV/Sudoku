package com.sudoku.exception;

public class DifficultyNotFoundException extends SudokuException {

    public DifficultyNotFoundException(String name) {
        super("Dificulty '" + name + "' not found. Please select a valid difficulty level.");
    }
}
