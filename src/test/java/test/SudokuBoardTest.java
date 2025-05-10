package test;

import com.sudoku.model.DifficultyGame;
import com.sudoku.model.SudokuBoard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class SudokuBoardTest {

    private static int[][] solutionSolved;
    private SudokuBoard sudokuBoard;


    @BeforeAll
    static void initTemplate() {
        solutionSolved = new int[][]{
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
    }


    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Running test: " + testInfo.getDisplayName());
        sudokuBoard = new SudokuBoard(solutionSolved, DifficultyGame.EASY);

        int[][] copiedBoard = deepCopy(solutionSolved);
        sudokuBoard = new SudokuBoard(copiedBoard, DifficultyGame.EASY);
    }


    @Test
    @DisplayName("Constructor removes correct cells number of all difficulty")
    void constructorRemoveCellsCorrectly() {
        for (DifficultyGame difficult : DifficultyGame.values()) {
            int[][] copyToFindOut = deepCopy(solutionSolved);
            SudokuBoard sudokuBoardCopyOfCase = new SudokuBoard(copyToFindOut, difficult);
            assertZeros(difficult.getRemovedCells(), sudokuBoardCopyOfCase);
        }
    }


    @Test
    @DisplayName("resetGame() updates solutions and removes specified cells")
    void resetGame() {
        int removeCells = 10;
        int[][] solutionAux = deepCopy(solutionSolved);
        sudokuBoard.resetGame(solutionAux, removeCells);

        assertZeros(removeCells, sudokuBoard);
        assertArrayEquals(solutionSolved, solutionAux);
    }


    @ParameterizedTest(name = "removeCells({0}) removes exactly {0} cells")
    @ValueSource(ints = {0, 1, 5, 81})
    @DisplayName("removeCells() remoces the given number of cells")
    void removeCells(int removeCountCells) {
        sudokuBoard.resetGame(deepCopy(solutionSolved), 0);
        sudokuBoard.removeCells(removeCountCells);

        assertZeros(removeCountCells, sudokuBoard);
    }


    @Test
    @DisplayName("isCorrect() returns true when current board matches solution")
    void isCorrect() {
        sudokuBoard.resetGame(deepCopy(solutionSolved), 0);
        assertTrue(sudokuBoard.isCorrect());
    }


    @Test
    @DisplayName("isCorrect return flase when a cell is incorrect")
    void isCorrectFalse() {
        sudokuBoard.resetGame(deepCopy(solutionSolved), 0);
        sudokuBoard.setCurrentValue(0, 0, 1);
        assertFalse(sudokuBoard.isCorrect());
    }


    @Test
    @DisplayName("getCurrentValue(), setCurrentValue() and getSolutionValue() valid access")
    void getAndSetCurrentAndSolutionValues() {
        sudokuBoard.resetGame(deepCopy(solutionSolved), 0);
        int row = 4, col = 7;
        int solVal = sudokuBoard.getSolutionValue(row, col);

        assertEquals(solutionSolved[row][col], solVal);
        assertEquals(solVal, sudokuBoard.getCurrentValue(row, col));

        int newVal = (solVal % 9) + 1;
        sudokuBoard.setCurrentValue(row, col, newVal);

        assertEquals(newVal, sudokuBoard.getCurrentValue(row, col));
        assertEquals(solVal, sudokuBoard.getSolutionValue(row, col));
    }


    // Helper methods

    private static int[][] deepCopy(int[][] original) {
        return Arrays.stream(original)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    private static void assertZeros(int expected, SudokuBoard sudokuBoard) {
        int actual = countZeros(sudokuBoard);

        assertEquals(
                expected,
                actual,
                String.format("Expected: %s zeros found %s", expected, actual)
        );
    }

    private static int countZeros(SudokuBoard sudokuBoard) {
        int zeros = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.getCurrentValue(row, col) == 0) zeros++;
            }
        }

        return zeros;
    }
}