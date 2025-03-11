/**
 * Tests Created by Kristian Wright
 */
package ModelTests;

import Model.GameBoard;
import Model.Property;
import Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Property class.
 */
public class PropertyTest {

    private Property property;
    private Player player;
    private GameBoard gameBoard;

    /**
     * Sets up the test environment before each test.
     * Initializes a new GameBoard, Player, and Property.
     */
    @BeforeEach
    public void setUp() {
        gameBoard = new GameBoard(new ArrayList<>());
        player = new Player("Test Player", "Token", gameBoard);
        property = new Property("Boardwalk", 400, "Blue");
    }

    /**
     * Tests the Property constructor.
     * Verifies that the name, price, and color group are correctly set.
     */
    @Test
    public void testPropertyConstructor() {
        assertEquals("Boardwalk", property.getName());
        assertEquals(400, property.getPrice());
        assertEquals("Blue", property.getColorGroup());
    }

    /**
     * Tests the buy method of the Property class.
     * Verifies that buying a property correctly sets the owner and decreases the player's money.
     */
    @Test
    public void testBuyProperty() {
        property.buy(player);
        assertEquals(player, property.getOwner());
        assertEquals(1100, player.getMoney());
    }

    /**
     * Tests the payRent method of the Property class.
     * Verifies that paying rent correctly transfers money from the player to the property owner.
     */
    @Test
    public void testPayRent() {
        property.buy(player);
        Player otherPlayer = new Player("Other Player", "Token", gameBoard);
        int initialMoneyOtherPlayer = otherPlayer.getMoney();
        int initialMoneyPlayer = player.getMoney();
        int rentAmount = property.calculateRent();
        property.payRent(otherPlayer);
        assertEquals(initialMoneyOtherPlayer - rentAmount, otherPlayer.getMoney());
        assertEquals(initialMoneyPlayer + rentAmount, player.getMoney());
    }

    /**
     * Tests the mortgage method of the Property class.
     * Verifies that mortgaging a property correctly sets the mortgaged status and increases the player's money.
     */
    @Test
    public void testMortgageProperty() {
        property.buy(player);
        property.mortgage();
        assertTrue(property.isMortgaged());
        assertEquals(1300, player.getMoney());
    }

    /**
     * Tests the unmortgage method of the Property class.
     * Verifies that unmortgaging a property correctly removes the mortgaged status and decreases the player's money.
     */
    @Test
    public void testUnmortgageProperty() {
        property.buy(player); // initial money was 1500 - 400 = 1100
        property.mortgage(); // 1100 after mortgaging the property + 200 = 1300
        property.unmortgage(); // Then after unmortgaging the property (with 10% interest) = 1300 - 220 = 1080
        assertFalse(property.isMortgaged());
        assertEquals(1080, player.getMoney()); // Corrected expected value
    }
}