package ViewTests;

import Model.*;
import View.MonopolyView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;

public class MonopolyViewTest {

    private GameBoard gameBoard;
    private MonopolyView view;
    private Player player;

    @BeforeEach
    public void setUp() {
        List<Player> players = new ArrayList<>();
        gameBoard = new GameBoard(players);
        view = new MonopolyView(gameBoard);
        player = new Player("TestPlayer", "Car", gameBoard);
        players.add(player);
    }

    @Test
    public void testDisplaySpaces() {
        // Capture the output
        view.displaySpaces();
        // Assertions can be added based on expected output
    }

    @Test
    public void testDisplayShuffledChanceCards() {
        gameBoard.shuffleChanceCards();
        Stack<ChanceCard> chanceDeck = gameBoard.getChanceDeck();
        view.displayShuffledChanceCards();
        // Assertions can be added based on expected output
    }

    @Test
    public void testDisplayShuffledCommunityChestCards() {
        gameBoard.shuffleCommunityChestCards();
        Stack<CommunityChestCard> communityDeck = gameBoard.getCommunityDeck();
        view.displayShuffledCommunityChestCards();
        // Assertions can be added based on expected output
    }

    @Test
    public void testDisplayDiceRoll() {
        int initialPosition = player.getPosition();
        view.displayDiceRoll(player);
        int finalPosition = player.getPosition();
        assertNotEquals(initialPosition, finalPosition, "Player position should change after rolling dice.");
    }
}