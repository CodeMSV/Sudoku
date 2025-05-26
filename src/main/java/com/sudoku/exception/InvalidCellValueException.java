package com.sudoku.exception;

public class InvalidCellValueException extends SudokuException {

    public InvalidCellValueException(int row, int col, int value) {
        super(String.format("Invalid value %d in (%d,%d)", value, row, col));
    }
}
