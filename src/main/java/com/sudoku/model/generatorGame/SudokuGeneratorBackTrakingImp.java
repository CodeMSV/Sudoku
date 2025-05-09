package com.sudoku.model.generatorGame;

import com.sudoku.model.DifficultyGame;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGeneratorBackTrakingImp implements SudokuGenerator {

    /**
     * Generates a Sudoku puzzle based on the specified difficulty level.
     *
     * @param difficultyGame The difficulty level of the Sudoku puzzle to generate.
     * @return A 2D array representing the generated Sudoku puzzle.
     */
    @Override
    public int[][] generate(DifficultyGame difficultyGame) {
        int[][] boardGame = new int[9][9];
        fillBoardGame(boardGame);
        return boardGame;
    }

    /**
     * Fills the Sudoku grid with numbers from 1 to 9 using backtracking.
     *
     * @param boardGame The Sudoku grid to fill.
     * @return true if the grid is completely filled, false otherwise.
     */
    private boolean fillBoardGame(int[][] boardGame) {
        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {
                if (boardGame[row][col] == 0) {
                    List<Integer> numbers = IntStream.rangeClosed(1, 9)
                            .boxed()
                            .collect(Collectors.toList());
                    Collections.shuffle(numbers);

                    for (int num : numbers) {
                        if (isValid(boardGame, row, col, num)) {
                            boardGame[row][col] = num;
                            if (fillBoardGame(boardGame)) return true;
                            boardGame[row][col] = 0;
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Valide/Check if a number can be placed in a cell of de Sudoku grid board.
     *
     * @param boardGame The Sudoku grid.
     * @param row       The row index.
     * @param col       The column index.
     * @param num       The number to check.
     * @return true if the number can be placed, false otherwise.
     */
    private boolean isValid(int[][] boardGame, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (boardGame[row][i] == num || boardGame[i][col] == num) {
                return false;
            }
        }

        int blockStartRowIndex = (row / 3) * 3;
        int blockStartColIndex = (col / 3) * 3;

        for (int r = blockStartRowIndex; r < blockStartRowIndex + 3; r++) {

            for (int c = blockStartColIndex; c < blockStartColIndex + 3; c++) {
                if (boardGame[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}