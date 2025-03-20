package View;

import Model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MonopolyView {

    private final GameBoard gameBoard;

    public MonopolyView(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void displaySpaces() {
        List<Space> spaces = gameBoard.getSpaces();
        System.out.println("Game Board Spaces\n_____________________________________");
        for (int i = 0; i < spaces.size(); i++) {
            Space space = spaces.get(i);
            System.out.println(i + ": " + space.getName());
            switch (space) {
                case Space.PropertySpace property -> {
                    System.out.println("  Price: $" + property.getPrice());
                    System.out.println("  Rent: $" + property.calculateRent());
                    System.out.println("  Owner: " + (property.isOwned() ? property.getOwner().getName() : "None"));
                }
                case Space.RailroadSpace railroad -> {
                    System.out.println("  Price: $" + railroad.getPrice());
                    System.out.println("  Owner: " + (railroad.isOwned() ? railroad.getOwner().getName() : "None"));
                }
                case Space.UtilitySpace utility -> {
                    System.out.println("  Price: $" + utility.getPrice());
                    System.out.println("  Owner: " + (utility.isOwned() ? utility.getOwner().getName() : "None"));
                }
                case Space.TaxSpace tax -> System.out.println("  Tax Amount: $" + tax.getTaxAmount());
                default -> {
                }
            }
        }
        System.out.println("_____________________________________");
    }

    public void displayShuffledChanceCards() {
        Stack<ChanceCard> chanceDeck = gameBoard.getChanceDeck();
        System.out.println("Shuffled Chance Cards");
        for (ChanceCard card : chanceDeck) {
            System.out.println(card.getDescription());
        }
        printSeparator();
    }

    public void displayShuffledCommunityChestCards() {
        Stack<CommunityChestCard> communityDeck = gameBoard.getCommunityDeck();
        System.out.println("Shuffled Community Chest Cards");
        for (CommunityChestCard card : communityDeck) {
            System.out.println(card.getDescription());
        }
        printSeparator();
    }

    public void displayDiceRoll(Player player) {
        int startPosition = player.getPosition();
        player.rollDice(); // Player rolls dice
        int endPosition = player.getPosition();
        Space startSpace = gameBoard.getSpace(startPosition);
        Space endSpace = gameBoard.getSpace(endPosition);
        int distanceMoved = (endPosition - startPosition + gameBoard.getSpaces().size()) % gameBoard.getSpaces().size();

        System.out.println(player.getToken() + " started at " + startSpace.getName() + " (Position: " + startPosition + ")");
        System.out.println(player.getToken() + " moved " + distanceMoved + " spaces to " + endSpace.getName() + " (Position: " + endPosition + ").");

        switch (endSpace) {
            case Space.PropertySpace property -> {
                System.out.println("  Price: $" + property.getPrice());
                System.out.println("  Rent: $" + property.calculateRent());
                System.out.println("  Owner: " + (property.isOwned() ? property.getOwner().getName() : "None"));
            }
            case Space.RailroadSpace railroad -> {
                System.out.println("  Price: $" + railroad.getPrice());
                System.out.println("  Owner: " + (railroad.isOwned() ? railroad.getOwner().getName() : "None"));
            }
            case Space.UtilitySpace utility -> {
                System.out.println("  Price: $" + utility.getPrice());
                System.out.println("  Owner: " + (utility.isOwned() ? utility.getOwner().getName() : "None"));
            }
            case Space.TaxSpace tax -> System.out.println("  Tax Amount: $" + tax.getTaxAmount());
            case Space.ChanceSpace _ -> {
                ChanceCard card = gameBoard.getChanceDeck().pop();
                System.out.println(player.getName() + " drew a Chance card: " + card.getDescription());
                card.apply(player);
                gameBoard.getChanceDeck().push(card); // Optionally, put the card back at the bottom of the deck

            }
            case Space.CommunityChestSpace _ -> {
                CommunityChestCard card = gameBoard.getCommunityDeck().pop();
                System.out.println(player.getName() + " drew a Community Chest card: " + card.getDescription());
                card.apply(player);
                gameBoard.getCommunityDeck().push(card); // Optionally, put the card back at the bottom of the deck

            }
            default -> {
            }
        }

        printSeparator();
    }

    private void printSeparator() {
        System.out.println("_____________________________________");
    }

    public static void main(String[] args) {
        GameBoard board = new GameBoard(new ArrayList<>());
        Player topHat = new Player("Player 1", "Top Hat", board);
        Player battleship = new Player("Player 2", "Battleship", board);

        MonopolyView view = new MonopolyView(board);

        // Display game board spaces
        view.displaySpaces();

        // Shuffle and display Chance cards
        board.shuffleChanceCards();
        view.displayShuffledChanceCards();

        // Shuffle and display Community Chest cards
        board.shuffleCommunityChestCards();
        view.displayShuffledCommunityChestCards();

        // Simulation loop
        while (true) {
            System.out.println("Player 1's turn");
            view.displayDiceRoll(topHat);
            if (topHat.getPosition() == 0) {
                System.out.println("Player 1 reached Go!");
                break;
            }

            System.out.println("Player 2's turn");
            view.displayDiceRoll(battleship);
            if (battleship.getPosition() == 0) {
                System.out.println("Player 2 reached Go!");
                break;
            }
        }
    }
}