/**
 * Tests Created by Kristian Wright and Rachele Grigoli
 */
package ModelTests;

import Model.GameBoard;
import Model.Player;
import Model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Space class and its subclasses.
 */
public class SpaceTest {

    private Space space;
    private Player player;

    /**
     * Sets up the test environment before each test.
     * Initializes a new GameBoard and Player.
     */
    @BeforeEach
    public void setUp() {
        GameBoard gameBoard = new GameBoard(new ArrayList<>());
        player = new Player("Test Player", "Token", gameBoard);
    }

    /**
     * Tests the constructors of PropertySpace and UtilitySpace with valid names.
     * Verifies that the names are correctly set.
     */
    @Test
    public void testSpaceConstructorWithValidName() {
        space = new Space.PropertySpace("Boardwalk", 39, "Blue", 400, 50,
                100, 200, 600, 1400, 1700,
                2000, 200, 200);
        assertEquals("Boardwalk", space.getName());
        space = new Space.UtilitySpace("Electric Company", 12, 150, 75);
        assertEquals("Electric Company", space.getName());
    }

    /**
     * Tests the constructor of GoSpace with an empty string.
     * Verifies that the name is correctly set to "Go".
     */
    @Test
    public void testSpaceConstructorWithEmptyString() {
        space = new Space.GoSpace();
        assertEquals("Go", space.getName());
    }

    /**
     * Tests the constructor of GoSpace with a null name.
     * Verifies that the name is not null.
     */
    @Test
    public void testSpaceConstructorWithNullName() {
        space = new Space.GoSpace();
        assertNotNull(space.getName());
    }

    /**
     * Tests the landOn method of GoSpace.
     * Verifies that the player collects $200 when landing on Go.
     */
    @Test
    public void testGoSpaceLandOn() {
        space = new Space.GoSpace();
        int initialMoney = player.getMoney();
        space.landOn(player);
        assertEquals(initialMoney + 200, player.getMoney());
    }

    /**
     * Tests the landOn method of CommunityChestSpace.
     * Verifies the expected behavior of drawing a Community Chest card.
     */
    @Test
    public void testCommunityChestSpaceLandOn() {
        space = new Space.CommunityChestSpace();
        space.landOn(player);
        // Add assertions based on the expected behavior of drawing a Community Chest card
    }

    /**
     * Tests the landOn method of IncomeTaxSpace.
     * Verifies that the player pays $200 when landing on Income Tax.
     */
    @Test
    public void testIncomeTaxSpaceLandOn() {
        space = new Space.IncomeTaxSpace();
        int initialMoney = player.getMoney();
        space.landOn(player);
        assertEquals(initialMoney - 200, player.getMoney());
    }

    /**
     * Tests the landOn method of RailroadSpace.
     * Verifies the expected behavior of landing on a Railroad space.
     */
    @Test
    public void testRailroadSpaceLandOn() {
        space = new Space.RailroadSpace("Reading Railroad", 5, 200, 25, 50, 100, 200, 100);
        space.landOn(player);
        // Add assertions based on the expected behavior of landing on a Railroad space
    }

    /**
     * Tests the landOn method of ChanceSpace.
     * Verifies the expected behavior of drawing a Chance card.
     */
    @Test
    public void testChanceSpaceLandOn() {
        space = new Space.ChanceSpace();
        space.landOn(player);
        // Add assertions based on the expected behavior of drawing a Chance card
    }

    /**
     * Tests the landOn method of JailSpace.
     * Verifies the expected behavior of landing on Jail space.
     */
    @Test
    public void testJailSpaceLandOn() {
        space = new Space.JailSpace();
        space.landOn(player);
        // Add assertions based on the expected behavior of landing on Jail space
    }

    /**
     * Tests the landOn method of UtilitySpace.
     * Verifies the expected behavior of landing on a Utility space.
     */
    @Test
    public void testUtilitySpaceLandOn() {
        space = new Space.UtilitySpace("Electric Company", 12, 150, 75);
        space.landOn(player);
        // Add assertions based on the expected behavior of landing on a Utility space
    }

    /**
     * Tests the landOn method of PropertySpace.
     * Verifies the expected behavior of landing on a Property space.
     */
    @Test
    public void testPropertySpaceLandOn() {
        space = new Space.PropertySpace("Boardwalk", 39, "Blue", 400, 50,
                100, 200, 600, 1400, 1700,
                2000, 200, 200);
        space.landOn(player);
        // Add assertions based on the expected behavior of landing on a Property space
    }

    /**
     * Tests the landOn method of FreeParkingSpace.
     * Verifies the expected behavior of landing on Free Parking space.
     */
    @Test
    public void testFreeParkingSpaceLandOn() {
        space = new Space.FreeParkingSpace();
        space.landOn(player);
        // Add assertions based on the expected behavior of landing on Free Parking space
    }

    /**
     * Tests the landOn method of GoToJailSpace.
     * Verifies that the player is sent to jail when landing on Go-To Jail space.
     */
    @Test
    public void testGoToJailSpaceLandOn() {
        space = new Space.GoToJailSpace();
        space.landOn(player);
        assertTrue(player.isInJail());
    }

    /**
     * Tests the landOn method of LuxuryTaxSpace.
     * Verifies that the player pays $75 when landing on Luxury Tax.
     */
    @Test
    public void testLuxuryTaxSpaceLandOn() {
        space = new Space.LuxuryTaxSpace();
        int initialMoney = player.getMoney();
        space.landOn(player);
        assertEquals(initialMoney - 75, player.getMoney());
    }

    /**
     * Tests the landOn method of TaxSpace.
     * Verifies that the player pays the specified tax amount when landing on a Tax space.
     */
    @Test
    public void testTaxSpaceLandOn() {
        space = new Space.TaxSpace("Income Tax", 4, 200);
        int initialMoney = player.getMoney();
        space.landOn(player);
        assertEquals(initialMoney - 200, player.getMoney());
    }
}