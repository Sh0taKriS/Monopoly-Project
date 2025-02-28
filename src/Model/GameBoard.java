import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

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

        // createGameBoard();
        // initializeChanceCards();
        // initializeCommunityChestCards();
        // shuffleChanceCards();
        // shuffleCommunityCards();
        // assignTokensToPlayers();
        // distributeStartingMoney();
    }
/*
    // Step 2: Create board spaces
    private void createGameBoard() {

        spaces.add(new Space.GoSpace()); // position 0
        spaces.add(new Property("Mediterranean Avenue", 60, "Brown"));
        spaces.add(new Space.CommunityChestSpace());
        spaces.add(new Property("Baltic Avenue", 60, "Brown"));
        spaces.add(new Space.IncomeTaxSpace());
        spaces.add(new Space.RailroadSpace("Reading Railroad", 200));
        spaces.add(new Property("Oriental Avenue", 100, "Light Blue"));
        spaces.add(new Space.ChanceSpace());
        spaces.add(new Property("Vermont Avenue", 100, "Light Blue"));
        spaces.add(new Property("Connecticut Avenue", 120, "Light Blue"));
        spaces.add(new Space.JailSpace());
        spaces.add(new Property("St. Charles Place", 140, "Pink"));
        spaces.add(new Space.UtilitySpace("Electric Company", 150, utilityCost, utilityMortgageValue));
        spaces.add(new Property("States Avenue", 140, "Pink"));
        spaces.add(new Property("Virginia Avenue", 160, "Pink"));
        spaces.add(new Space.RailroadSpace("Pennsylvania Railroad", 200));
        spaces.add(new Property("St. James Place", 180, "Orange"));
        spaces.add(new Space.CommunityChestSpace());
        spaces.add(new Property("Tennessee Avenue", 180, "Orange"));
        spaces.add(new Property("New York Avenue", 200, "Orange"));
        spaces.add(new Space.FreeParkingSpace());
        spaces.add(new Property("Kentucky Avenue", 220, "Red"));
        spaces.add(new Space.ChanceSpace());
        spaces.add(new Property("Indiana Avenue", 220, "Red"));
        spaces.add(new Property("Illinois Avenue", 240, "Red"));
        spaces.add(new Space.RailroadSpace("B&O Railroad", 200));
        spaces.add(new Property("Atlantic Avenue", 260, "Yellow"));
        spaces.add(new Property("Ventnor Avenue", 260, "Yellow"));
        spaces.add(new Space.UtilitySpace("Water Works", 150, utilityCost, utilityMortgageValue));
        spaces.add(new Property("Marvin Gardens", 280, "Yellow"));
        spaces.add(new Space.GoToJailSpace());
        spaces.add(new Property("Pacific Avenue", 300, "Green"));
        spaces.add(new Property("North Carolina Avenue", 300, "Green"));
        spaces.add(new Space.CommunityChestSpace());
        spaces.add(new Property("Pennsylvania Avenue", 320, "Green"));
        spaces.add(new Space.RailroadSpace("Short Line", 200));
        spaces.add(new Space.ChanceSpace());
        spaces.add(new Property("Park Place", 350, "Blue"));
        spaces.add(new Space.LuxuryTaxSpace());
        spaces.add(new Property("Boardwalk", 400, "Blue"));
    }
*/
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

        chanceDeck.add(new ChanceCard("Advance to Boardwalk", player -> player.setPosition(39))); // Assuming 39 is Boardwalk
        chanceDeck.add(new ChanceCard("Advance to Go (Collect $200)", player -> {player.setPosition(0); player.increaseMoney(200);}));
        chanceDeck.add(new ChanceCard("Go to Jail – Go directly to jail – Do not pass Go – Do not collect $200", player -> player.setPosition(10))); // Assuming 10 is Jail
        chanceDeck.add(new ChanceCard("Pay school fees of $150", player -> player.decreaseMoney(150)));
        chanceDeck.add(new ChanceCard("Bank pays you a dividend of $50", player -> player.increaseMoney(50)));
        chanceDeck.add(new ChanceCard("Your building loan matures – Collect $150", player -> player.increaseMoney(150)));
        chanceDeck.add(new ChanceCard("You have won a crossword competition – Collect $100", player -> player.increaseMoney(100)));
        chanceDeck.add(new ChanceCard("Advance to Illinois Avenue. If you pass Go, collect $200", player -> {if (player.getPosition() > 24) {player.increaseMoney(200);}player.setPosition(24);})); // Assuming 24 is Illinois Avenue
        chanceDeck.add(new ChanceCard("Advance to St. Charles Place. If you pass Go, collect $200", player -> {if (player.getPosition() > 11) {player.increaseMoney(200);}player.setPosition(11);})); // Assuming 11 is St. Charles Place
        chanceDeck.add(new ChanceCard("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled", this::moveToNearestRailroad));
        chanceDeck.add(new ChanceCard("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown", this::moveToNearestUtility));
        chanceDeck.add(new ChanceCard("Take a trip to Reading Railroad. If you pass Go, collect $200", player -> {if (player.getPosition() > 5) {player.increaseMoney(200);}player.setPosition(5);})); // Assuming 5 is Reading Railroad
        chanceDeck.add(new ChanceCard("Speeding fine $15", player -> player.decreaseMoney(15)));
        chanceDeck.add(new ChanceCard("Take a walk on the Boardwalk", player -> player.setPosition(39))); // Assuming 39 is Boardwalk
        chanceDeck.add(new ChanceCard("Go back 3 spaces", player -> player.setPosition(player.getPosition() - 3)));
        chanceDeck.add(new ChanceCard("Make general repairs on all your property – For each house pay $25, For each hotel $100", this::makeGeneralRepairs));
        chanceDeck.add(new ChanceCard("Pay poor tax of $15", player -> player.decreaseMoney(15)));
        chanceDeck.add(new ChanceCard("You have been elected Chairman of the Board – Pay each player $50", player -> payEachPlayer(player, 50)));
        chanceDeck.add(new ChanceCard("Your building and loan matures – Collect $150", player -> player.increaseMoney(150)));
        chanceDeck.add(new ChanceCard("You have won a crossword competition – Collect $100", player -> player.increaseMoney(100)));
        chanceDeck.add(new ChanceCard("Get Out of Jail Free – This card may be kept until needed or sold", Player::receiveGetOutOfJailFreeCard));

    }

    // Step 4: Initialize Community Chest cards
    private void initializeCommunityChestCards() {
        communityDeck.add(new CommunityChestCard("Advance to Go (Collect $200)", player -> {player.setPosition(0);player.increaseMoney(200);}));
        communityDeck.add(new CommunityChestCard("Doctor’s fees – Pay $50", player -> player.decreaseMoney(50)));
        communityDeck.add(new CommunityChestCard("Bank error in your favor – Collect $200", player -> player.increaseMoney(200)));
        communityDeck.add(new CommunityChestCard("From sale of stock you get $50", player -> player.increaseMoney(50)));
        communityDeck.add(new CommunityChestCard("Get Out of Jail Free – This card may be kept until needed or sold", Player::receiveGetOutOfJailFreeCard));
        communityDeck.add(new CommunityChestCard("Go to Jail – Go directly to jail – Do not pass Go – Do not collect $200", player -> player.setPosition(10))); // Assuming 10 is Jail
        communityDeck.add(new CommunityChestCard("Grand Opera Night – Collect $50 from every player for opening night seats", player -> collectFromEachPlayer(player, 50)));
        communityDeck.add(new CommunityChestCard("Holiday Fund matures – Receive $100", player -> player.increaseMoney(100)));
        communityDeck.add(new CommunityChestCard("Income tax refund – Collect $20", player -> player.increaseMoney(20)));
        communityDeck.add(new CommunityChestCard("It is your birthday – Collect $10 from every player", player -> collectFromEachPlayer(player, 10)));
        communityDeck.add(new CommunityChestCard("Life insurance matures – Collect $100", player -> player.increaseMoney(100)));
        communityDeck.add(new CommunityChestCard("Pay hospital fees of $100", player -> player.decreaseMoney(100)));
        communityDeck.add(new CommunityChestCard("Pay school fees of $50", player -> player.decreaseMoney(50)));
        communityDeck.add(new CommunityChestCard("Receive $25 consultancy fee", player -> player.increaseMoney(25)));
        communityDeck.add(new CommunityChestCard("You are assessed for street repairs – $40 per house – $115 per hotel", this::assessStreetRepairs));
        communityDeck.add(new CommunityChestCard("You have won second prize in a beauty contest – Collect $10", player -> player.increaseMoney(10)));
        communityDeck.add(new CommunityChestCard("You inherit $100", player -> player.increaseMoney(100)));
        communityDeck.add(new CommunityChestCard("You go to the local school’s car wash fundraiser – but you forget to close your windows! PAY $100", player -> player.decreaseMoney(100)));
        communityDeck.add(new CommunityChestCard("You organize a block party so people on your street can get to know each other. COLLECT $10 FROM EACH PLAYER", player -> collectFromEachPlayer(player, 10)));
        communityDeck.add(new CommunityChestCard("You set aside time every week to hang out with your elderly neighbor – you’ve heard some amazing stories! COLLECT $100", player -> player.increaseMoney(100)));
        communityDeck.add(new CommunityChestCard("You help build a new school playground – then you get to test the slide! COLLECT $100", player -> player.increaseMoney(100)));
    }

    // Helper methods for card actions
    private void moveToNearestUtility(Player player) {
        // Logic to move player to nearest utility
    }

    private void moveToNearestRailroad(Player player) {
        // Logic to move player to nearest railroad
    }

    private void makeGeneralRepairs(Player player) {
        // Logic to make general repairs on player's properties
    }

    private void payEachPlayer(Player player, int amount) {
        // Logic to pay each player a specified amount
    }

    private void collectFromEachPlayer(Player player, int amount) {
        // Logic to collect a specified amount from each player
    }

    private void assessStreetRepairs(Player player) {
        // Logic to assess street repairs on player's properties
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