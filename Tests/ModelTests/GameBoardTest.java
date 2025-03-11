package ModelTests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GameBoard class.
 */
public class GameBoardTest {

    private GameBoard gameBoard;
    private List<Player> players;

    /**
     * Sets up the test environment before each test.
     * Initializes a new GameBoard and a list of players.
     */
    @BeforeEach
    public void setUp() {
        players = new ArrayList<>();
        gameBoard = new GameBoard(players);
        players.add(new Player("Player 1", "Car", gameBoard));
        players.add(new Player("Player 2", "Dog", gameBoard));
    }

    /**
     * Tests the initialization of the game board.
     * Verifies that the board has 40 spaces.
     */
    @Test
    public void testInitializeBoard() {
        assertEquals(40, gameBoard.getSpaces().size()); // Check if board has 40 spaces
    }

    /**
     * Tests the movePlayer method of the GameBoard class.
     * Verifies that the player moves to the correct position.
     */
    @Test
    public void testMovePlayer() {
        Player player = players.getFirst();
        gameBoard.movePlayer(player, 10);
        assertEquals(10, player.getPosition());
    }

    /**
     * Tests the passing of the Go space.
     * Verifies that the player collects $200 for passing Go.
     */
    @Test
    public void testPassGo() {
        Player player = players.getFirst();
        player.setPosition(39);
        gameBoard.movePlayer(player, 2);
        assertEquals(1, player.getPosition());
        assertEquals(1700, player.getMoney()); // Player should collect $200 for passing Go
    }

    /**
     * Tests landing on the Go space.
     * Verifies that the player collects $200 for landing on Go.
     */
    @Test
    public void testLandOnGo() {
        Player player = players.getFirst();
        gameBoard.movePlayer(player, 0);
        assertEquals(0, player.getPosition());
        assertEquals(1700, player.getMoney()); // Player should collect $200 for landing on Go
    }

    /**
     * Tests drawing a Chance card.
     * Verifies that the player receives the correct effect from the Chance card.
     */
    @Test
    public void testDrawChanceCard() {
        Player player = players.getFirst();
        gameBoard.getChanceDeck().push(new ChanceCard("Test Chance Card", p -> p.increaseMoney(100)));
        player.setPosition(7); // Directly set player position to Chance space
        System.out.println("Player money before drawing card: " + player.getMoney());
        gameBoard.getSpace(7).landOn(player); // Ensure player lands on Chance space
        System.out.println("Player money after drawing card: " + player.getMoney());
        assertEquals(1600, player.getMoney()); // Player should have $1600 after drawing the card
    }

    /**
     * Tests drawing a Community Chest card.
     * Verifies that the player receives the correct effect from the Community Chest card.
     */
    @Test
    public void testDrawCommunityChestCard() {
        Player player = players.getFirst();
        gameBoard.getCommunityDeck().push(new CommunityChestCard("Test Community Chest Card", p -> p.increaseMoney(100)));
        player.setPosition(2); // Directly set player position to Community Chest space
        System.out.println("Player money before drawing card: " + player.getMoney());
        gameBoard.getSpace(2).landOn(player); // Ensure player lands on Community Chest space
        System.out.println("Player money after drawing card: " + player.getMoney());
        assertEquals(1600, player.getMoney()); // Player should have $1600 after drawing the card
    }

    /**
     * Tests the Go-To Jail space.
     * Verifies that the player is sent to jail when landing on the Go-To Jail space.
     */
    @Test
    public void testGoToJail() {
        Player player = players.getFirst();
        gameBoard.movePlayer(player, 30); // Assuming Go To Jail space is at position 30
        assertTrue(player.isInJail());
        assertEquals(10, player.getPosition()); // Player should be at Jail position
    }
}