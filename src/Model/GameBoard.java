package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameBoard {
    final private List<Space> spaces; // Board spaces
    final private Stack<ChanceCard> chanceDeck; // Chance card deck
    final private Stack<CommunityChestCard> communityDeck; // Community Chest card deck
    final private List<Player> players; // Players in the game

    public GameBoard(List<Player> players) {
        this.players = players;
        this.spaces = new ArrayList<>();
        this.chanceDeck = new Stack<>();
        this.communityDeck = new Stack<>();
        initializeBoard();
    }

    // Step 1: Initialize the game board with properties and spaces
    private void initializeBoard() {
        UtilityGameBoardSpaces();
        RailroadGameBoardSpaces();
        initializeChanceCards();
        initializeCommunityChestCards();
        shuffleChanceCards();
        shuffleCommunityCards();
        assignTokensToPlayers();
        distributeStartingMoney();
    }

    private void UtilityGameBoardSpaces() {
        try {
            List<String> lines = Files.readAllLines(Path.of("src/resources/Utilities.txt"));
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String[] parts = line.split(",\\s*");
                String name = parts[0];
                int utilitySpaceLocation = Integer.parseInt(parts[1]);
                int utilityCost = Integer.parseInt(parts[2]);
                int utilityMortgageValue = Integer.parseInt(parts[3]);
                spaces.add(new Space.UtilitySpace(name, utilitySpaceLocation, utilityCost, utilityMortgageValue));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void RailroadGameBoardSpaces() {
        try {
            List<String> lines = Files.readAllLines(Path.of("src/resources/Railroads.txt"));
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String[] parts = line.split(",\\s*");
                String name = parts[0];
                int railroadSpaceLocation = Integer.parseInt(parts[1]);
                int railroadCost = Integer.parseInt(parts[2]);
                int railroadCostWithOne = Integer.parseInt(parts[3]);
                int railroadCostWithTwo = Integer.parseInt(parts[4]);
                int railroadCostWithThree = Integer.parseInt(parts[5]);
                int railroadCostWithFour = Integer.parseInt(parts[6]);
                int railroadMortgageValue = Integer.parseInt(parts[2]); // Corrected mortgage value
                spaces.add(new Space.RailroadSpace(name, railroadSpaceLocation, railroadCost, railroadCostWithOne,
                        railroadCostWithTwo, railroadCostWithThree, railroadCostWithFour, railroadMortgageValue));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Step 3: Initialize Chance cards
    private void initializeChanceCards() {
        try {
            List<String> lines = Files.readAllLines(Path.of("src/resources/ChanceCard.txt"));
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String description = line.trim();
                chanceDeck.add(new ChanceCard(description, ChanceCard.getEffect(description)));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Step 4: Initialize Community Chest cards
    private void initializeCommunityChestCards() {
        try {
            List<String> lines = Files.readAllLines(Path.of("src/resources/CommunityCard.txt"));
            for (String line : lines) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String description = line.trim();
                communityDeck.add(new CommunityChestCard(description, CommunityChestCard.getEffect(description)));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Step 5: Shuffle Chance cards
    private void shuffleChanceCards() {
        Collections.shuffle(chanceDeck);
    }

    // Step 6: Shuffle Community Chest cards
    private void shuffleCommunityCards() {
        Collections.shuffle(communityDeck);
    }

    // Step 7: Assign tokens to players
    private void assignTokensToPlayers() {
        List<String> availableTokens = Arrays.asList("Car", "Dog", "Hat", "Boot", "Ship");
        Collections.shuffle(availableTokens);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setToken(availableTokens.get(i));
        }
    }

    // Step 8: Distribute $1500 to each player
    private void distributeStartingMoney() {
        for (Player player : players) {
            player.setMoney(1500);
        }
    }

    // Move a player on the board
    public void movePlayer(Player player, int steps) {
        int oldPosition = player.getPosition();
        int newPosition = (oldPosition + steps) % spaces.size();
        // Check if the player passed "Go" and collect $200
        if (oldPosition > newPosition) {
            player.increaseMoney(200);
            System.out.println(player.getName() + " passed Go and collected $200!");
        }
        player.setPosition(newPosition);
        System.out.println(player.getName() + " moved to " + spaces.get(newPosition).getName());
        // Execute space action
        spaces.get(newPosition).landOn(player);
    }

    // Get space at a position
    public Space getSpace(int position) {
        return spaces.get(position);
    }
}