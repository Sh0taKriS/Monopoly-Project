package Model;

import java.util.*;

/**
 * The GameBoard class represents the game board in a Monopoly game.
 * It manages the spaces, Chance and Community Chest decks, and the players.
 */
public class GameBoard {
    private final List<Space> spaces; // Board spaces
    private final Stack<ChanceCard> chanceDeck; // Chance card deck
    private final Stack<CommunityChestCard> communityDeck; // Community Chest card deck
    private final List<Player> players; // Players in the game

    /**
     * Constructs a GameBoard with the given list of players.
     * Initializes the board spaces and card decks.
     *
     * @param players The list of players in the game.
     */
    public GameBoard(List<Player> players) {
        this.players = players;
        this.spaces = new ArrayList<>();
        this.chanceDeck = new Stack<>();
        this.communityDeck = new Stack<>();
        initializeBoard();
    }

    /**
     * Initializes the game board with properties and spaces.
     */
    private void initializeBoard() {
        spaces.add(new Space.GoSpace()); // 0: Go
        spaces.add(new Space.PropertySpace("Mediterranean Avenue", 1, "Brown", 60, 2, 4, 10, 30, 90, 160, 250, 30, 50)); // 1
        spaces.add(new Space.CommunityChestSpace()); // 2: Community Chest
        spaces.add(new Space.PropertySpace("Baltic Avenue", 3, "Brown", 60, 4, 8, 20, 60, 180, 320, 450, 30, 50)); // 3
        spaces.add(new Space.TaxSpace("Income Tax", 4, 200)); // 4: Income Tax
        spaces.add(new Space.RailroadSpace("Reading Railroad", 5, 200, 25, 50, 100, 200, 100)); // 5
        spaces.add(new Space.PropertySpace("Oriental Avenue", 6, "Light Blue", 100, 6, 12, 30, 90, 270, 400, 550, 50, 50)); // 6
        spaces.add(new Space.ChanceSpace()); // 7: Chance
        spaces.add(new Space.PropertySpace("Vermont Avenue", 8, "Light Blue", 100, 6, 12, 30, 90, 270, 400, 550, 50, 50)); // 8
        spaces.add(new Space.PropertySpace("Connecticut Avenue", 9, "Light Blue", 120, 8, 16, 40, 100, 300, 450, 600, 60, 50)); // 9
        spaces.add(new Space.JailSpace()); // 10: Jail
        spaces.add(new Space.PropertySpace("St Charles Place", 11, "Pink", 140, 10, 20, 50, 150, 450, 625, 750, 70, 100)); // 11
        spaces.add(new Space.UtilitySpace("Electric Company", 12, 150, 75)); // 12
        spaces.add(new Space.PropertySpace("States Avenue", 13, "Pink", 140, 10, 20, 50, 150, 450, 625, 750, 70, 100)); // 13
        spaces.add(new Space.PropertySpace("Virginia Avenue", 14, "Pink", 160, 12, 24, 60, 180, 500, 700, 900, 80, 100)); // 14
        spaces.add(new Space.RailroadSpace("Pennsylvania Railroad", 15, 200, 25, 50, 100, 200, 100)); // 15
        spaces.add(new Space.PropertySpace("St James Place", 16, "Orange", 180, 14, 28, 70, 200, 550, 750, 950, 90, 100)); // 16
        spaces.add(new Space.CommunityChestSpace()); // 17: Community Chest
        spaces.add(new Space.PropertySpace("Tennessee Avenue", 18, "Orange", 180, 14, 28, 70, 200, 550, 750, 950, 90, 100)); // 18
        spaces.add(new Space.PropertySpace("New York Avenue", 19, "Orange", 200, 16, 32, 80, 220, 600, 800, 1000, 100, 100)); // 19
        spaces.add(new Space.FreeParkingSpace()); // 20: Free Parking
        spaces.add(new Space.PropertySpace("Kentucky Avenue", 21, "Red", 220, 18, 36, 90, 250, 700, 875, 1050, 110, 150)); // 21
        spaces.add(new Space.ChanceSpace()); // 22: Chance
        spaces.add(new Space.PropertySpace("Indiana Avenue", 23, "Red", 220, 18, 36, 90, 250, 700, 875, 1050, 110, 150)); // 23
        spaces.add(new Space.PropertySpace("Illinois Avenue", 24, "Red", 240, 20, 40, 100, 300, 750, 925, 1100, 120, 150)); // 24
        spaces.add(new Space.RailroadSpace("B & O Railroad", 25, 200, 25, 50, 100, 200, 100)); // 25
        spaces.add(new Space.PropertySpace("Atlantic Avenue", 26, "Yellow", 260, 22, 44, 110, 330, 800, 975, 1150, 130, 150)); // 26
        spaces.add(new Space.PropertySpace("Ventnor Avenue", 27, "Yellow", 260, 22, 44, 110, 330, 800, 975, 1150, 130, 150)); // 27
        spaces.add(new Space.UtilitySpace("Water Works", 28, 150, 75)); // 28
        spaces.add(new Space.PropertySpace("Marvin Gardens", 29, "Yellow", 280, 24, 48, 120, 360, 850, 1025, 1200, 140, 150)); // 29
        spaces.add(new Space.GoToJailSpace()); // 30: Go To Jail
        spaces.add(new Space.PropertySpace("Pacific Avenue", 31, "Green", 300, 26, 52, 130, 390, 900, 1100, 1275, 150, 200)); // 31
        spaces.add(new Space.PropertySpace("North Carolina Avenue", 32, "Green", 300, 26, 52, 130, 390, 900, 1100, 1275, 150, 200)); // 32
        spaces.add(new Space.CommunityChestSpace()); // 33: Community Chest
        spaces.add(new Space.PropertySpace("Pennsylvania Avenue", 34, "Green", 320, 28, 56, 150, 450, 1000, 1200, 1400, 160, 200)); // 34
        spaces.add(new Space.RailroadSpace("Short Line Railroad", 35, 200, 25, 50, 100, 200, 100)); // 35
        spaces.add(new Space.ChanceSpace()); // 36: Chance
        spaces.add(new Space.PropertySpace("Park Place", 37, "Blue", 350, 35, 70, 175, 500, 1100, 1300, 1500, 175, 200)); // 37
        spaces.add(new Space.TaxSpace("Luxury Tax", 38, 75)); // 38: Luxury Tax
        spaces.add(new Space.PropertySpace("Boardwalk", 39, "Blue", 400, 50, 100, 200, 600, 1400, 1700, 2000, 200, 200)); // 39

        initializeChanceCards();
        initializeCommunityChestCards();
        shuffleChanceCards();
        shuffleCommunityChestCards();
        assignTokensToPlayers();
        distributeStartingMoney();
    }

    /**
     * Initializes the Chance cards.
     */
    private void initializeChanceCards() {
        chanceDeck.add(new ChanceCard("Advance to Boardwalk.", player -> player.setPosition(39)));
        chanceDeck.add(new ChanceCard("Advance to Go (Collect $200).", player -> {player.setPosition(0); player.increaseMoney(200);}));
        chanceDeck.add(new ChanceCard("Advance to Illinois Avenue. If you pass Go, collect $200.", player -> {if (player.getPosition() > 24) {player.increaseMoney(200);} player.setPosition(24);}));
        chanceDeck.add(new ChanceCard("Advance to St. Charles Place. If you pass Go, collect $200.", player -> {if (player.getPosition() > 11) {player.increaseMoney(200);} player.setPosition(11);}));
        chanceDeck.add(new ChanceCard("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled.", _ -> ChanceCard.moveToNearestRailroad()));
        chanceDeck.add(new ChanceCard("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled.", _ -> ChanceCard.moveToNearestRailroad()));
        chanceDeck.add(new ChanceCard("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.", _ -> ChanceCard.moveToNearestUtility()));
        chanceDeck.add(new ChanceCard("Bank pays you dividend of $50.", player -> player.increaseMoney(50)));
        chanceDeck.add(new ChanceCard("Get Out of Jail Free.", Player::receiveGetOutOfJailFreeCard));
        chanceDeck.add(new ChanceCard("Go Back 3 Spaces.", player -> player.move(-3)));
        chanceDeck.add(new ChanceCard("Go to Jail. Go directly to Jail, do not pass Go, do not collect $200.", Player::goToJail));
        chanceDeck.add(new ChanceCard("Make general repairs on all your property. For each house pay $25. For each hotel pay $100.", _ -> ChanceCard.makeGeneralRepairs()));
        chanceDeck.add(new ChanceCard("Speeding fine $15.", player -> player.decreaseMoney(15)));
        chanceDeck.add(new ChanceCard("Take a trip to Reading Railroad. If you pass Go, collect $200.", player -> {if (player.getPosition() > 5) {player.increaseMoney(200);} player.setPosition(5);}));
        chanceDeck.add(new ChanceCard("You have been elected Chairman of the Board. Pay each player $50.", _ -> ChanceCard.payEachPlayer()));
        chanceDeck.add(new ChanceCard("Your building loan matures. Collect $150.", player -> player.increaseMoney(150)));
    }

    /**
     * Initializes the Community Chest cards.
     */
    private void initializeCommunityChestCards() {
        communityDeck.add(new CommunityChestCard("Advance to Go (Collect $200).", player -> {player.setPosition(0); player.increaseMoney(200);}));
        communityDeck.add(new CommunityChestCard("Bank error in your favor. Collect $200.", player -> player.increaseMoney(200)));
        communityDeck.add(new CommunityChestCard("Doctor’s fee. Pay $50.", player -> player.decreaseMoney(50)));
        communityDeck.add(new CommunityChestCard("From sale of stock you get $50.", player -> player.increaseMoney(50)));
        communityDeck.add(new CommunityChestCard("Get Out of Jail Free.", Player::receiveGetOutOfJailFreeCard));
        communityDeck.add(new CommunityChestCard("Go to Jail. Go directly to jail, do not pass Go, do not collect $200.", Player::goToJail));
        communityDeck.add(new CommunityChestCard("Holiday fund matures. Receive $100.", player -> player.increaseMoney(100)));
        communityDeck.add(new CommunityChestCard("Income tax refund. Collect $20.", player -> player.increaseMoney(20)));
        communityDeck.add(new CommunityChestCard("It is your birthday. Collect $10 from every player.", _ -> CommunityChestCard.collectFromEachPlayer()));
        communityDeck.add(new CommunityChestCard("Life insurance matures. Collect $100.", player -> player.increaseMoney(100)));
        communityDeck.add(new CommunityChestCard("Pay hospital fees of $100.", player -> player.decreaseMoney(100)));
        communityDeck.add(new CommunityChestCard("Pay school fees of $50.", player -> player.decreaseMoney(50)));
        communityDeck.add(new CommunityChestCard("Receive $25 consultancy fee.", player -> player.increaseMoney(25)));
        communityDeck.add(new CommunityChestCard("You are assessed for street repair. $40 per house. $115 per hotel.", CommunityChestCard.assessStreetRepairs));
        communityDeck.add(new CommunityChestCard("You have won second prize in a beauty contest. Collect $10.", player -> player.increaseMoney(10)));
        communityDeck.add(new CommunityChestCard("You inherit $100.", player -> player.increaseMoney(100)));
    }

    /**
     * Shuffles the Chance cards.
     */
    public void shuffleChanceCards() {
        Collections.shuffle(chanceDeck);
    }

    /**
     * Shuffles the Community Chest cards.
     */
    public void shuffleCommunityChestCards() {
        Collections.shuffle(communityDeck);
    }

    /**
     * Assigns tokens to players randomly.
     */
    public void assignTokensToPlayers() {
        List<String> availableTokens = new ArrayList<>(Arrays.asList(
                "Top Hat", "Battleship", "Thimble", "Cannon", "Cat", "Iron",
                "Scottie dog", "The Shoe", "Boot", "Ducky", "Horse & Rider",
                "Penguin", "Race car", "Train", "Wheelbarrow"
        ));
        Scanner scanner = new Scanner(System.in);

        for (Player player : players) {
            System.out.println(player.getName() + ", choose your token from the following list:");
            for (int j = 0; j < availableTokens.size(); j++) {
                System.out.println((j + 1) + ": " + availableTokens.get(j));
            }

            int choice = -1;
            while (choice < 1 || choice > availableTokens.size()) {
                System.out.print("Enter the number of your choice: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            String chosenToken = availableTokens.remove(choice - 1);
            player.setToken(chosenToken);
            System.out.println(player.getName() + " has chosen the " + chosenToken + " token.");
        }
    }

    /**
     * Distributes the starting money to each player.
     */
    public void distributeStartingMoney() {
        for (Player player : players) {
            player.setMoney(1500);
        }
    }

    /**
     * Moves a player by a specified number of steps.
     * If the player passes Go, they collect $200.
     *
     * @param player The player to move.
     * @param steps The number of steps to move.
     */
    public void movePlayer(Player player, int steps) {
        int oldPosition = player.getPosition();
        int newPosition = (oldPosition + steps) % spaces.size();
        if (oldPosition > newPosition) {
            player.increaseMoney(200);
            System.out.println(player.getName() + " passed Go and collected $200!");
        }
        player.setPosition(newPosition);
        System.out.println(player.getName() + " moved to " + spaces.get(newPosition).getName());
        spaces.get(newPosition).landOn(player);
    }

    /**
     * Gets the space at a specified position.
     *
     * @param position The position of the space.
     * @return The space at the specified position.
     */
    public Space getSpace(int position) {
        return spaces.get(position);
    }

    /**
     * Gets the Chance card deck.
     *
     * @return The Chance card deck.
     */
    public Stack<ChanceCard> getChanceDeck() {
        return chanceDeck;
    }

    /**
     * Gets the Community Chest card deck.
     *
     * @return The Community Chest card deck.
     */
    public Stack<CommunityChestCard> getCommunityDeck() {
        return communityDeck;
    }

    /**
     * Gets the list of spaces on the board.
     *
     * @return The list of spaces.
     */
    public List<Space> getSpaces() {
        return spaces;
    }
}