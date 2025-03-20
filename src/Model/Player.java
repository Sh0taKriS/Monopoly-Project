package Model;

import java.util.*;

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

    public Player(String name, String token, GameBoard gameBoard) {
        this.name = name;
        this.token = token;
        this.money = 1500;
        this.position = 0;
        this.properties = new ArrayList<>();
        this.inJail = false;
        this.jailTurns = 0;
        this.hasGetOutOfJailFreeCard = false;
        this.gameBoard = gameBoard;
        this.dice = new Dice();
    }

    public void move(int steps) {
        int oldPosition = position;
        position = (position + steps) % 40;
        Space newSpace = gameBoard.getSpace(position);
        System.out.println(token + " moved to " + newSpace.getName());
    }

    public void goToJail() {
        this.inJail = true;
        this.position = 10;
        System.out.println(token + " is sent to Jail.");
    }

    public void rollDice() {
        dice.rollDice(this);
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void increaseMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }

    public boolean isInJail() {
        return inJail;
    }

    public boolean hasGetOutOfJailFreeCard() {
        return hasGetOutOfJailFreeCard;
    }

    public void receiveGetOutOfJailFreeCard() {
        this.hasGetOutOfJailFreeCard = true;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public Dice getDice() {
        return dice;
    }
}