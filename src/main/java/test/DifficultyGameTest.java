package test;

import com.sudoku.model.DifficultyGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DifficultyGameTest {


    @Test
    void getRemovedCells() {
        assertEquals(35, DifficultyGame.EASY.getRemovedCells(), "Easy difficulty should have 35 removed cells");
        assertEquals(45, DifficultyGame.MEDIUM.getRemovedCells(), "Medium difficulty should have 45 removed cells");
        assertEquals(55, DifficultyGame.HARD.getRemovedCells(), "Hard difficulty should have 55 removed cells");
    }

    @Test
    void values() {
        DifficultyGame[] expectedValues = {DifficultyGame.EASY, DifficultyGame.MEDIUM, DifficultyGame.HARD};
        DifficultyGame[] actualValues = DifficultyGame.values();
        assertArrayEquals(expectedValues, actualValues, "The values method should return the correct array of DifficultyGame enums");

        assertEquals(3, actualValues.length, "There should be 3 difficulty levels");
    }

    @Test
    void valueOf() {
    }
}
