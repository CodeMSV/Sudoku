package com.sudoku.model;

import com.sudoku.controller.SudokuController;
import com.sudoku.model.generatorGame.SudokuGenerator;
import com.sudoku.model.generatorGame.SudokuGeneratorBackTrakingImp;
import com.sudoku.view.MainFrame;

import javax.swing.*;

public class SudokuAppRunner {

    private SudokuController controller;

    public void ejecutar() {
        SwingUtilities.invokeLater(() -> {
            SudokuGenerator generator = new SudokuGeneratorBackTrakingImp();
            DifficultyGame defaultDifficulty = DifficultyGame.MEDIUM;
            int[][] initialSolution = generator.generate(defaultDifficulty);
            SudokuBoard model = new SudokuBoard(initialSolution, defaultDifficulty);
            MainFrame view = new MainFrame();
            controller = new SudokuController(model, view, generator);
            view.setVisible(true);
        });
    }
}
