package com.sudoku.model;

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
}
