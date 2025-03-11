/**
 * Class Created by Kristian Wright
 */
package Model;

import java.util.*;

/**
 * Represents a player in the Monopoly game.
 * Each player has a name, token, money, position on the board, properties, and other attributes.
 */
public class Player {
    final private String name;
    private String token;
    private int money;
    private int position;
    final private List<Property> properties;
    private boolean inJail;
    private int jailTurns;
    private boolean hasGetOutOfJailFreeCard;
    private Dice dice;
    private GameBoard gameBoard;

    /**
     * Constructs a Player with the given name, token, and game board.
     *
     * @param name The name of the player.
     * @param token The token representing the player.
     * @param gameBoard The game board the player is playing on.
     */
    public Player(String name, String token, GameBoard gameBoard) {
        this.name = name;
        this.token = token;
        this.money = 1500; // Standard Monopoly starting money
        this.position = 0; // Start at "Go"
        this.properties = new ArrayList<>();
        this.inJail = false;
        this.jailTurns = 0;
        this.hasGetOutOfJailFreeCard = false;
        this.gameBoard = gameBoard;
    }

    /**
     * Moves the player by a specified number of steps.
     *
     * @param steps The number of steps to move.
     */
    public void move(int steps) {
        position = (position + steps) % 40; // Wrap around board
        System.out.println(name + " moved to position " + position);
    }

    /**
     * Buys a property for the player if they have enough money and the property is unowned.
     *
     * @param property The property to buy.
     */
    public void buyProperty(Property property) {
        if (money >= property.getPrice() && !property.isOwned()) {
            property.buy(this);
            money -= property.getPrice();
            properties.add(property);
            System.out.println(name + " bought " + property.getName() + " for $" + property.getPrice());
        } else {
            System.out.println(name + " cannot afford " + property.getName());
        }
    }

    /**
     * Pays rent to the owner of a property if the property is owned by another player.
     *
     * @param property The property to pay rent for.
     */
    public void payRent(Property property) {
        if (property.isOwned() && property.getOwner() != this) {
            int rent = property.calculateRent();
            if (money >= rent) {
                money -= rent;
                property.getOwner().increaseMoney(rent);
                System.out.println(name + " paid $" + rent + " rent to " + property.getOwner().getName());
            } else {
                System.out.println(name + " doesn't have enough money to pay rent!");
            }
        }
    }

    /**
     * Mortgages a property owned by the player.
     *
     * @param property The property to mortgage.
     */
    public void mortgageProperty(Property property) {
        if (properties.contains(property) && !property.isMortgaged()) {
            property.mortgage();
            money += property.getPrice() / 2;
            System.out.println(name + " mortgaged " + property.getName() + " for $" + (property.getPrice() / 2));
        }
    }

    /**
     * Sends the player to jail.
     */
    public void goToJail() {
        this.inJail = true;
        this.position = 10; // Set position to Jail
        System.out.println(name + " is sent to Jail.");
    }

    /**
     * Gets the player out of jail using the specified method.
     *
     * @param method The method to get out of jail ("pay", "card", or "roll").
     */
    public void getOutOfJail(String method) {
        if (method.equals("pay")) {
            if (money >= 50) {
                money -= 50;
                inJail = false;
                jailTurns = 0;
                System.out.println(name + " paid $50 to get out of jail.");
            } else {
                System.out.println(name + " doesn't have enough money to pay bail.");
            }
        } else if (method.equals("card")) {
            if (hasGetOutOfJailFreeCard) {
                hasGetOutOfJailFreeCard = false;
                inJail = false;
                jailTurns = 0;
                System.out.println(name + " used a 'Get Out of Jail Free' card!");
            } else {
                System.out.println(name + " does not have a 'Get Out of Jail Free' card!");
            }
        } else if (method.equals("roll")) {
            if (dice.rollJail()) {
                inJail = false;
                jailTurns = 0;
                System.out.println(name + " rolled doubles and got out of jail!");
            } else {
                jailTurns++;
                System.out.println(name + " did not roll doubles and remains in jail.");
                if (jailTurns >= 3) {
                    inJail = false;
                    jailTurns = 0;
                    System.out.println(name + " must pay $50 and is now out of jail.");
                    money -= 50;
                }
            }
        }
    }

    /**
     * Rolls the dice for the player.
     */
    private void rollDice() {
        dice.rollDice(this);
    }

    /**
     * Increases the player's money by a specified amount.
     *
     * @param amount The amount to increase.
     */
    public void increaseMoney(int amount) {
        money += amount;
    }

    /**
     * Decreases the player's money by a specified amount.
     *
     * @param amount The amount to decrease.
     */
    public void decreaseMoney(int amount) {
        money -= amount;
    }

    /**
     * Gets the player's current position on the board.
     *
     * @return The player's position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the player's position on the board.
     *
     * @param position The new position.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's current amount of money.
     *
     * @return The player's money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the player's money to a specified amount.
     *
     * @param money The new amount of money.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Sets the player's token.
     *
     * @param token The new token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Checks if the player is in jail.
     *
     * @return True if the player is in jail, false otherwise.
     */
    public boolean isInJail() {
        return inJail;
    }

    /**
     * Gets the list of properties owned by the player.
     *
     * @return The list of properties.
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Adds a property to the player's list of owned properties.
     *
     * @param property The property to add.
     */
    public void addProperty(Property property) {
        properties.add(property);
    }

    /**
     * Checks if the player has a "Get Out of Jail Free" card.
     *
     * @return True if the player has the card, false otherwise.
     */
    public boolean hasGetOutOfJailFreeCard() {
        return hasGetOutOfJailFreeCard;
    }

    /**
     * Gives the player a "Get Out of Jail Free" card.
     */
    public void receiveGetOutOfJailFreeCard() {
        this.hasGetOutOfJailFreeCard = true;
    }

    /**
     * Gets the player's token.
     *
     * @return The player's token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the game board the player is playing on.
     *
     * @return The game board.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }
}