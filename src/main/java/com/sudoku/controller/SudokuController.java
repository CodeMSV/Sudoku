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
        initController();
        newGame();
    }

    /**
     * Initializes the controller by adding action listeners to the buttons.
     */
    private void initController() {
        view.newGameBtn.addActionListener(e -> newGame());
        view.difficultyCombo.addActionListener(e -> newGame());
        view.checkBtn.addActionListener(e -> checkBoard());
        view.hintBtn.addActionListener(e -> giveHint());
        view.solveBtn.addActionListener(e -> solveBoard());
    }


    private void newGame() {
        DifficultyGame difficultyGame = (DifficultyGame) view.difficultyCombo.getSelectedItem();
        int[][] solutionGame = generator.generate(difficultyGame);
        model.resetGame(solutionGame, difficultyGame.getRemovedCells());
        setHintsByDifficulty(difficultyGame);
        updateBoard();
    }

    /**
     * Sets the number of hints remaining based on the selected difficulty level.
     *
     * @param difficultyGame The selected difficulty level.
     */
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

    /**
     * Checks if the Sudoku board is solved correctly and displays a message accordingly.
     */
    private void checkBoard() {
        syncModelFromView();
        boolean checkVerify = model.isCorrect();

        JOptionPane.showMessageDialog(view,
                checkVerify ? "¡Enhorabuena! Sudoku resuelto correctamente."
                        : "Hay errores. Sigue intentándolo.",
                "Resultado",
                checkVerify ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.WARNING_MESSAGE);

    }

    /**
     * Synchronizes the model with the current values in the view.
     */
    private void syncModelFromView() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String valueText = view.cells[row][col].getText().trim();

                try {
                    model.setCurrentValue(row, col, Integer.parseInt(valueText));
                } catch (NumberFormatException e) {
                    model.setCurrentValue(row, col, 0);
                }

            }

        }
    }

    /**
     * Provides a hint by filling in the first empty cell with the correct value.
     */
    private void giveHint() {
        if (hintsRemaining <= 0) {
            JOptionPane.showMessageDialog(view,
                    "No quedan pistas.",
                    "Hint",
                    JOptionPane.INFORMATION_MESSAGE);
            view.hintBtn.setEnabled(false);
            return;
        }

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {
                if (model.getCurrentValue(row, col) == 0) {

                    int hint = model.getSolutionValue(row, col);
                    model.setCurrentValue(row, col, hint);

                    JTextField cell = view.cells[row][col];
                    cell.setText(String.valueOf(hint));
                    cell.setEditable(false);
                    cell.setForeground(Color.MAGENTA);

                    hintsRemaining--;

                    view.hintBtn.setText("💡 Hint (" + hintsRemaining + ")");

                    if (hintsRemaining == 0) {
                        view.hintBtn.setEnabled(false);
                    }
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(view,
                "No quedan huecos para una pista.",
                "Hint",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Solves the Sudoku board and displays the solution in the view.
     */
    private void solveBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int sol = model.getSolutionValue(row, col);
                model.setCurrentValue(row, col, sol);
                JTextField cell = view.cells[row][col];
                cell.setText(String.valueOf(sol));
                cell.setEditable(false);
                cell.setForeground(Color.RED);
            }
        }
        view.hintBtn.setEnabled(false);
        JOptionPane.showMessageDialog(view,
                "Sudoku completado.",
                "Solve",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
