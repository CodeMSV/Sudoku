package com.sudoku.model.generatorGame;

import com.sudoku.model.DifficultyGame;

public interface SudokuGenerator {

    /**
     * Generate a Sudoku game based on the specified difficulty level.
     *
     *
     * @param difficultyGame level of the sudoku select for player
     * @return a 2D array representing the generated sudoku game.
     */
    int[][] generate(DifficultyGame difficultyGame);
}
