package com.sudoku.controller;

import com.sudoku.exception.InvalidCellValueException;
import com.sudoku.model.DifficultyGame;
import com.sudoku.model.SudokuBoard;
import com.sudoku.model.generatorGame.SudokuGenerator;
import com.sudoku.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
        addCellListeners();
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
                    int value = valueText.isEmpty() ? 0 : Integer.parseInt(valueText);
                    model.setCurrentValue(row, col, value);
                } catch (NumberFormatException | InvalidCellValueException ex) {
                    view.cells[row][col].setText("");
                    JOptionPane.showMessageDialog(
                            view,
                            ex.getMessage(),
                            "Valor no válido",
                            JOptionPane.WARNING_MESSAGE
                    );
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
                "Sudoky completado.",
                "Solve",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Adds action listeners to each cell in the Sudoku board.
     */
    private void addCellListeners() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField cell = view.cells[row][col];
                attachCellListener(row, col, cell);
            }
        }
    }

    /**
     * Attaches a listener to a cell that handles input and validation.
     *
     * @param row  The row index of the cell.
     * @param col  The column index of the cell.
     * @param cell The JTextField representing the cell.
     */
    private void attachCellListener(int row, int col, JTextField cell) {
        cell.addActionListener(e -> handleCellInput(row, col, cell));
        cell.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                handleCellInput(row, col, cell);
            }
        });
    }

    /**
     * Handles the input for a cell, validating and updating the model.
     *
     * @param row  The row index of the cell.
     * @param col  The column index of the cell.
     * @param cell The JTextField representing the cell.
     */
    private void handleCellInput(int row, int col, JTextField cell) {
        String text = cell.getText().trim();
        try {
            int value = text.isEmpty() ? 0 : Integer.parseInt(text);
            model.setCurrentValue(row, col, value);
            cell.setText(value == 0 ? "" : String.valueOf(value));
        } catch (NumberFormatException | InvalidCellValueException ex) {
            cell.setText("");
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Valor no válido",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

}
