package com.sudoku.model;

import java.util.Arrays;
import java.util.Random;

public class SudokuBoard {

    private int[][] solutionBoarGame;
    private int[][] boardCurrentGame;

    /**
     * Constructor that initializes the Sudoku board with a solution and a difficulty level.
     *
     * @param solutionSolved  The solved Sudoku board.
     * @param difficultyGame  The difficulty level of the game.
     */
    public SudokuBoard(int[][] solutionSolved, DifficultyGame difficultyGame ) {
        this.solutionBoarGame = Arrays.stream(solutionSolved)
                .map(int[]::clone)
                .toArray(int[][]::new);

        this.boardCurrentGame = Arrays.stream(solutionSolved)
                .map(int[]::clone)
                .toArray(int[][]::new);


        removeCells(difficultyGame.getRemovedCells());
    }

    /**
     * Constructor that initializes the Sudoku board with a solution and a specified number of cells to remove.
     *
     * @param solutionSolved  The solved Sudoku board.
     * @param removeCountCells The number of cells to remove from the board.
     */
    public void resetGame(int[][] newSolutionGame, int removeCountCells) {
        this.solutionBoarGame = Arrays.stream(newSolutionGame)
                .map(int[]::clone)
                .toArray(int[][]::new);

        this.boardCurrentGame = Arrays.stream(newSolutionGame)
                .map(int[]::clone)
                .toArray(int[][]::new);

        removeCells(removeCountCells);
    }


    /**
     * Removes a specified number of cells from the Sudoku board randomly.
     *
     * @param countCellsToRemove The number of cells to remove.
     */
    public void removeCells(int countCellsToRemove) {
        Random random = new Random();
        int removedCount = 0;

        while (removedCount < countCellsToRemove) {
            int i = random.nextInt(9);
            int j = random.nextInt(9);

            if (boardCurrentGame[i][j] != 0) {
                boardCurrentGame[i][j] = 0;
                removedCount++;
            }
        }
    }


    /**
     * Checks if the current Sudoku board is correct by comparing it with the solution board.
     *
     * @return true if the current board matches the solution, false otherwise.
     */
    public boolean isCorrect() {
        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {
                if (boardCurrentGame[row][col] != solutionBoarGame[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }



    public int getSolutionBoarGame(int row, int col) {
        return solutionBoarGame[row][col];
    }

    public void setValue(int row, int col, int value) {
        this.boardCurrentGame[row][col] = value;
    }

    public int getSolutionValue(int row, int col) {
        return solutionBoarGame[row][col];
    }
}
