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

    public Player(String name, String token) {
        this.name = name;
        this.token = token;
        this.money = 1500; // Standard Monopoly starting money
        this.position = 0; // Start at "Go"
        this.properties = new ArrayList<>();
        this.inJail = false;
        this.jailTurns = 0;
        this.hasGetOutOfJailFreeCard = false;
    }

    public void move(int steps) {
        position = (position + steps) % 40; // Wrap around board
        System.out.println(name + " moved to position " + position);
    }

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

    public void mortgageProperty(Property property) {
        if (properties.contains(property) && !property.isMortgaged()) {
            property.mortgage();
            money += property.getPrice() / 2;
            System.out.println(name + " mortgaged " + property.getName() + " for $" + (property.getPrice() / 2));
        }
    }

    public void goToJail() {this.inJail = true;}

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

    private void rollDice() {
        dice.rollDice(this);
    }

    public void increaseMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isInJail() {
        return inJail;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public boolean hasGetOutOfJailFreeCard() {
        return hasGetOutOfJailFreeCard;
    }

    public void receiveGetOutOfJailFreeCard() {
        this.hasGetOutOfJailFreeCard = true;
    }
}