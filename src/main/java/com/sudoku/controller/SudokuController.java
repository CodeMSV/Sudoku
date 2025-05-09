package com.sudoku.controller;

import com.sudoku.model.DifficultyGame;
import com.sudoku.model.SudokuBoard;
import com.sudoku.model.generatorGame.SudokuGenerator;
import com.sudoku.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class SudokuController {

    private final SudokuBoard model;
    private final MainFrame view;
    private final SudokuGenerator generator;
    private int hintsRemaining;

    public SudokuController(SudokuBoard model, MainFrame view, SudokuGenerator generator) {
        this.model = model;
        this.view = view;
        this.generator = generator;
        //initController();
        //newGame();
    }

    private void initController() {
       // view.newGameBtn.addActionListener(e -> newGame());
    }

    private void  newGame() {
        DifficultyGame difficultyGame = (DifficultyGame) view.difficultyCombo.getSelectedItem();
        int[][] solutionGame = generator.generate(difficultyGame);
        setHintsByDifficulty(difficultyGame);
        //updateBoard();
    }

    private void setHintsByDifficulty(DifficultyGame difficultyGame) {
        switch (difficultyGame) {
            case EASY -> hintsRemaining = 5;
            case MEDIUM -> hintsRemaining = 3;
            case HARD -> hintsRemaining = 2;
            default -> hintsRemaining = 3;
        }

        view.hintBtn.setText("💡 Hint (" + this.hintsRemaining + ")");
        view.hintBtn.setEnabled(true);
    }

    private void updateBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = view.cells[row][col];

                int value = model.getCurrentValue(row, col);

                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setForeground(Color.BLUE);
                } else {
                    cell.setText("");
                    cell.setEditable(true);
                    cell.setForeground(Color.BLACK);
                }

            }

        }
    }



}
