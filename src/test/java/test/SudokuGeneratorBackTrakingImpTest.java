package test;

import com.sudoku.model.DifficultyGame;
import com.sudoku.model.generatorGame.SudokuGeneratorBackTrakingImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGeneratorBackTrakingImpTest {

    private SudokuGeneratorBackTrakingImp generator;


    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Running test: " + testInfo.getDisplayName());
        generator = new SudokuGeneratorBackTrakingImp();
    }

    @Test
    @DisplayName("generate() returns a 9x9 board")
    void generate() {
        int[][] board = generator.generate(DifficultyGame.EASY);

        assertNotNull(board, "The board should not be null");
        assertEquals(9, board.length, "Board should have 9 rows");

        for (int[] row : board) {
            assertEquals(9, row.length, "Each row should hace 9 column");
        }
    }

    @Test
    @DisplayName("generate() fills all cells with values between 1 and 9")
    void generate_NoZerosAndValidRange() {
        int[][] board = generator.generate(DifficultyGame.MEDIUM);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = board[i][j];
                assertTrue(value >= 1 && value <= 9, "Num between 1 and 9");
            }
        }
    }

    @Test
    @DisplayName("generate() row contains numbers 1-9 without duplication")
    void generate_rowsContainAllNumbers() {
        int[][] board = generator.generate(DifficultyGame.HARD);

        for (int i = 0; i < 9; i++) {
            boolean[] seen = new boolean[10];

            for (int j = 0; j < 9; j++) {
                int value = board[i][j];
                seen[value] = true;
            }

            for (int n = 1; n <= 9; n++) {
                assertTrue(seen[n], "All value in row");
            }
        }
    }

    @Test
    @DisplayName("generate() each column contains numbers 1-9 without duplication")
    void generate_columnsContainAllNumbers() {
        int[][] board = generator.generate(DifficultyGame.HARD);

        for (int j = 0; j < 9; j++) {
            boolean[] seen = new boolean[10];

            for (int i = 0; i < 9; i++) {
                int value = board[i][j];
                seen[value] = true;
            }
            for (int n = 1; n <= 9; n++) {
                assertTrue(seen[n], "All value in column");
            }
        }
    }

    @Test
    @DisplayName("generate() each 3x3 block contains numbers 1-9 without duplication")
    void generate_blocksContainAllNumbers() {
        int[][] board = generator.generate(DifficultyGame.EASY);

        for (int blockRow = 0; blockRow < 3; blockRow++) {

            for (int blockCol = 0; blockCol < 3; blockCol++) {
                boolean[] seen = new boolean[10];

                for (int i = blockRow * 3; i < blockRow * 3 + 3; i++) {

                    for (int j = blockCol * 3; j < blockCol * 3 + 3; j++) {
                        int value = board[i][j];
                        seen[value] = true;
                    }
                }

                for (int n = 1; n <= 9; n++) {
                    assertTrue(seen[n]);
                }
            }
        }
    }
}