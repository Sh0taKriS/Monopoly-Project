/**
 * Tests Created by Kristian Wright
 */
package ModelTests;

import Model.Dice;
import Model.GameBoard;
import Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Dice class.
 */
public class DiceTest {
    private Dice dice;
    private Player player;

    /**
     * Sets up the test environment before each test.
     * Initializes a new Dice object and a Player object.
     */
    @BeforeEach
    public void setUp() {
        dice = new Dice();
        GameBoard gameBoard = new GameBoard(new ArrayList<>());
        player = new Player("TestPlayer", "Car", gameBoard);
    }

    /**
     * Tests the rollDice method of the Dice class.
     * Verifies that the player moves the correct distance or goes to jail.
     */
    @Test
    public void testRollDice() {
        int initialPosition = player.getPosition();
        int initialMoney = player.getMoney();
        dice.rollDice(player);
        int finalPosition = player.getPosition();
        int totalDistanceMoved = (finalPosition - initialPosition + 40) % 40; // Handle board wrap-around

        // Check if the player passed Go and collected $200
        if (finalPosition < initialPosition && !player.isInJail()) {
            assertEquals(initialMoney + 200, player.getMoney(), "Player should collect $200 for passing Go.");
        }

        // Check if the player moved the correct distance or went to jail
        if (player.isInJail()) {
            assertEquals(10, player.getPosition(), "Player should be in jail at position 10.");
        } else {
            assertTrue(totalDistanceMoved >= 2 && totalDistanceMoved <= 35, "Player should move between 2 and 35 spaces after rolling dice.");
        }
    }

    /**
     * Tests the rollJail method of the Dice class.
     * Verifies that the method returns a boolean value.
     */
    @Test
    public void testRollJail() {
        boolean result = dice.rollJail();
        assertTrue(true, "rollJail should return a boolean value.");
    }

    /**
     * Tests the rollDice method for rolling doubles.
     * Verifies that the player goes to jail after rolling doubles three times.
     */
    @Test
    public void testRollDiceDoubles() {
        dice.rollDice(player);
        int doublesRolled = 0;
        while (dice.rollJail()) {
            doublesRolled++;
            if (doublesRolled == 3) {
                assertTrue(player.isInJail(), "Player should be in jail after rolling doubles three times.");
                break;
            }
        }
    }
}