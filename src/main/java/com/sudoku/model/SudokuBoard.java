package com.sudoku.model;

import java.util.Arrays;

public class SudokuBoard {

    private int[][] solutionBoarGame;
    private int[][] boardCurrentGame;


    public SudokuBoard(int[][] solutionSolved, DifficultyGame difficultyGame ) {
        this.solutionBoarGame = Arrays.stream(solutionSolved)
                .map(int[]::clone)
                .toArray(int[][]::new);

        this.boardCurrentGame = Arrays.stream(solutionSolved)
                .map(int[]::clone)
                .toArray(int[][]::new);


        //removeCells(difficultyGame.getRemovedCells());
    }


    public void resetGame(int[][] newSolutionGame, int removeCountCells) {
        this.solutionBoarGame = Arrays.stream(newSolutionGame)
                .map(int[]::clone)
                .toArray(int[][]::new);

        this.boardCurrentGame = Arrays.stream(newSolutionGame)
                .map(int[]::clone)
                .toArray(int[][]::new);

        //removeCells(removeCountCells);
    }




}
