package com.sudoku.controller;

import com.sudoku.model.SudokuBoard;
import com.sudoku.model.generatorGame.SudokuGenerator;
import com.sudoku.view.MainFrame;

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

    }

}
