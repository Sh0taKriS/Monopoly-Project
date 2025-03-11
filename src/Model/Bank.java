/**
 * Class Created by Collin Castro supported by Kristian Wright
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Bank is responsible for:
 *  - Holding and selling unowned properties
 *  - Managing houses and hotels
 *  - Receiving taxes, fees, etc.
 *  - Paying players for passing GO or other earnings
 *  - Auctioning properties
 *  - Handling mortgages
 */
public class Bank {

    private static final int INITIAL_HOUSES = 32;
    private static final int INITIAL_HOTELS = 12;

    private int housesRemaining;
    private int hotelsRemaining;
    private final List<Property> unownedProperties;

    /**
     * Creates a Bank that starts off with the standard supply
     * of houses/hotels, and any unowned properties.
     *
     * @param allProperties The full list of properties in the game (these are initially unowned).
     */
    public Bank(List<Property> allProperties) {
        this.housesRemaining = INITIAL_HOUSES;
        this.hotelsRemaining = INITIAL_HOTELS;
        this.unownedProperties = new ArrayList<>();

        for (Property p : allProperties) {
            if (!p.isOwned()) {
                unownedProperties.add(p);
            }
        }
    }

    public int getHousesRemaining() {
        return housesRemaining;
    }

    public int getHotelsRemaining() {
        return hotelsRemaining;
    }

    /**
     * Gives a specified amount of money to a Player.
     *
     * @param player The player to receive the money.
     * @param amount The amount of money to give.
     */
    public void payPlayer(Player player, int amount) {
        player.increaseMoney(amount);
        System.out.println("Bank pays " + player.getName() + " $" + amount);
    }

    /**
     * Collects a specified amount of money from a Player.
     *
     * @param player The player to collect money from.
     * @param amount The amount of money to collect.
     */
    public void collectFromPlayer(Player player, int amount) {
        player.decreaseMoney(amount);
        System.out.println("Bank collects $" + amount + " from " + player.getName());
    }

    /**
     * Sells a property to a player at face value (assuming it is unowned).
     *
     * @param property The property to sell.
     * @param buyer The player buying the property.
     */
    public void sellProperty(Property property, Player buyer) {
        if (!property.isOwned()) {
            if (buyer.getMoney() >= property.getPrice()) {
                property.buy(buyer);
                unownedProperties.remove(property);
            } else {
                System.out.println(buyer.getName() + " cannot afford " + property.getName());
            }
        } else {
            System.out.println(property.getName() + " is already owned.");
        }
    }

    /**
     * Auctions a property among all players.
     *
     * @param property The property to auction.
     * @param players The list of players participating in the auction.
     */
    public void auctionProperty(Property property, List<Player> players) {
        unownedProperties.remove(property);
        System.out.println("Starting auction for " + property.getName() + " (Price: $" + property.getPrice() + ")");
        Scanner scanner = new Scanner(System.in);
        int highestBid = 0;
        Player highestBidder = null;

        for (Player p : players) {
            if (p.getMoney() <= 0) {
                continue;
            }

            System.out.println(p.getName() + ", enter your bid (0 to skip): ");
            int bid = 0;
            try {
                bid = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {}

            if (bid > highestBid && bid <= p.getMoney()) {
                highestBid = bid;
                highestBidder = p;
            }
        }

        if (highestBidder != null) {
            property.buy(highestBidder);
            System.out.println(highestBidder.getName() + " wins the auction for $" + highestBid + ".");
        } else {
            System.out.println("No bids were placed. The property remains with the Bank.");
            unownedProperties.add(property);
        }
    }

    /**
     * Mortgages a property (pays the player half its price).
     *
     * @param property The property to mortgage.
     * @param owner The owner of the property.
     */
    public void mortgageProperty(Property property, Player owner) {
        if (property.getOwner() != owner) {
            System.out.println("Cannot mortgage property you don't own!");
            return;
        }
        if (property.isMortgaged()) {
            System.out.println(property.getName() + " is already mortgaged.");
            return;
        }
        property.mortgage();
    }

    /**
     * Lifts a mortgage from a property if the owner has enough money.
     *
     * @param property The property to unmortgage.
     * @param owner The owner of the property.
     */
    public void unmortgageProperty(Property property, Player owner) {
        if (property.getOwner() != owner) {
            System.out.println("Cannot unmortgage property you don't own!");
            return;
        }
        if (!property.isMortgaged()) {
            System.out.println(property.getName() + " is not mortgaged.");
            return;
        }
        property.unmortgage();
    }

    public void printBankStatus() {
        System.out.println("\n--- Bank Status ---");
        System.out.println("Houses remaining: " + housesRemaining);
        System.out.println("Hotels remaining: " + hotelsRemaining);
        System.out.println("Unowned properties:");
        for (Property p : unownedProperties) {
            System.out.println("   - " + p.getName() + " ($" + p.getPrice() + ")");
        }
        System.out.println("-------------------\n");
    }
}