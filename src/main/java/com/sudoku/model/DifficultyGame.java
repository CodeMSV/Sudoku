package com.sudoku.model;

import com.sudoku.exception.DifficultyNotFoundException;

public enum DifficultyGame {

    EASY(35),
    MEDIUM(45),
    HARD(55);

    private final int removedCells;

    /**
     * Constructor that initializes the difficulty level with a specified number of cells to remove.
     *
     * @param removedCells The number of cells to remove for this difficulty level.
     */
    DifficultyGame(int removedCells) {
        this.removedCells = removedCells;
    }

    public int getRemovedCells() {
        return removedCells;
    }

    public static DifficultyGame fromString(String name) {
        if (name == null) {
            throw new DifficultyNotFoundException("null");
        }
        try {
            return valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DifficultyNotFoundException(name);
        }
    }
}
