package test;

import com.sudoku.model.DifficultyGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
class DifficultyGameTest {

    @BeforeEach
    void setUp(TestInfo info) {
        System.out.println("Running test: " + info.getDisplayName());
    }

    @ParameterizedTest(name = "{0} should remove {1} cells")
    @CsvSource({
            "EASY, 35",
            "MEDIUM, 45",
            "HARD, 55"
    })
    @DisplayName("getRemovedCells() returns correct number")
    void getRemovedCells(DifficultyGame difficulty, int expectedRemoved) {
        assertEquals(expectedRemoved, difficulty.getRemovedCells());
    }


    @Test
    @DisplayName("values() should contain all enum in order")
    void values() {
        DifficultyGame[] expectedValues = {DifficultyGame.EASY, DifficultyGame.MEDIUM, DifficultyGame.HARD};
        DifficultyGame[] actualValues = DifficultyGame.values();
        assertArrayEquals(expectedValues, actualValues, "The values method should return the correct array of DifficultyGame enums");

        assertEquals(3, actualValues.length, "There should be 3 difficulty levels");
    }

    @Test
    @DisplayName("values() length should be 3")
    void values_lenght() {
        DifficultyGame[] actualValues = {DifficultyGame.EASY, DifficultyGame.MEDIUM, DifficultyGame.HARD};
        assertEquals(3, actualValues.length, "There should be 3 difficulty levels");

    }

    @Test
    @DisplayName("valueOf(String) returns correct enum for valid names")
    void valueOf_validNames() {
        assertAll(
                () -> assertEquals(DifficultyGame.EASY, DifficultyGame.valueOf("EASY")),
                () -> assertEquals(DifficultyGame.MEDIUM, DifficultyGame.valueOf("MEDIUM")),
                () -> assertEquals(DifficultyGame.HARD, DifficultyGame.valueOf("HARD"))

        );
    }

    @Test
    @DisplayName("valueOf(null) throws NullPointerException")
    void valueOf_nullThrows() {
        assertThrows(NullPointerException.class,
                () -> DifficultyGame.valueOf(null),
                "valueOf(null) should throw NullPointerException");
    }
}
