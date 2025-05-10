package test;

import com.sudoku.controller.SudokuController;
import com.sudoku.model.DifficultyGame;
import com.sudoku.model.SudokuBoard;
import com.sudoku.model.generatorGame.SudokuGenerator;
import com.sudoku.model.generatorGame.SudokuGeneratorBackTrakingImp;
import com.sudoku.view.MainFrame;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;



class SudokuControllerTest {

    private SudokuController controller;
    private MainFrame view;
    private SudokuBoard board;
    private SudokuGeneratorBackTrakingImp generator;


    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Running test: " + testInfo.getDisplayName());

        generator = new SudokuGeneratorBackTrakingImp();

        view = new MainFrame();
        view.difficultyCombo.setModel(
                new DefaultComboBoxModel<>(DifficultyGame.values())
        );
        view.difficultyCombo.setSelectedItem(DifficultyGame.EASY);

        board = new SudokuBoard(generator.generate(DifficultyGame.EASY), DifficultyGame.EASY);
        controller = new SudokuController(board, view, generator);

    }


    @Test
    @DisplayName("newGame() initializes sudokuBoard and view corretly for EASY")
    void newGame_initEASY() {
        assertEquals("💡 Hint (5)", view.hintBtn.getText());
        assertTrue(view.hintBtn.isEnabled());

        int zeros = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCurrentValue(r, c) == 0) zeros++;
            }
        }
        assertEquals(DifficultyGame.EASY.getRemovedCells(), zeros);

    }

    @Test
    @DisplayName("setHintsByDifficulty() changing difficulty via combo re-initializes hints for MEDIUM and HARD")
    void setHintsByDifficulty_updates() {
        view.difficultyCombo.setSelectedItem(DifficultyGame.MEDIUM);

        assertEquals("💡 Hint (3)", view.hintBtn.getText());
        assertTrue(view.hintBtn.isEnabled());

        view.difficultyCombo.setSelectedItem(DifficultyGame.HARD);

        assertEquals("💡 Hint (2)", view.hintBtn.getText());
    }



















}