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
public class Bank 
{

    // Standard Monopoly typically includes 32 houses and 12 hotels (Double check if this is okay with client)
    private static final int INITIAL_HOUSES = 32;
    private static final int INITIAL_HOTELS = 12;

    private int housesRemaining;
    private int hotelsRemaining;

    // We can track unowned properties like this,
    // or use Property.owner == null to determine if the bank owns it.
    private final List<Property> unownedProperties;

    /**
     * Creates a Bank that starts off with the standard supply
     * of houses/hotels, and any unowned properties.
     *
     * @param allProperties The full list of properties in the game (these are initially unowned).
     */
    public Bank(List<Property> allProperties) 
    {
        this.housesRemaining = INITIAL_HOUSES;
        this.hotelsRemaining = INITIAL_HOTELS;
        this.unownedProperties = new ArrayList<>();

        // Initialize unownedProperties
        for (Property p : allProperties) 
        {
            if (!p.isOwned()) 
            {
                unownedProperties.add(p);
            }
        }
    }

    // --- GETTERS ---

    public int getHousesRemaining() 
    {
        return housesRemaining;
    }

    public int getHotelsRemaining() 
    {
        return hotelsRemaining;
    }

    // --- BANKING METHODS ---

    /**
     * Gives a specified amount of money to a Player.
     * Ex: Passing GO, Chance/Community Chest awards, etc.
     */
    public void payPlayer(Player player, int amount) 
    {
        player.increaseMoney(amount);
        System.out.println("Bank pays " + player.getName() + " $" + amount);
    }

    /**
     * Collects a specified amount of money from a Player.
     * Ex: taxes, fees, fines, etc.
     */
    public void collectFromPlayer(Player player, int amount) 
    {
        player.decreaseMoney(amount);
        System.out.println("Bank collects $" + amount + " from " + player.getName());
    }

    /**
     * Sells a property to a player at face value (assuming it is unowned).
     * This is a direct purchase with no auction.
     */
    public void sellProperty(Property property, Player buyer) 
    {
        if (!property.isOwned()) 
        {
            // If the buyer can afford it, transfer ownership
            if (buyer.getMoney() >= property.getPrice()) 
            {
                property.buy(buyer); // This should set owner and deduct money in your Property class
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
     * It is pretty simple so we can talk about further improvements
     * down the line if we want.
     */
    public void auctionProperty(Property property, List<Player> players) 
    {
        // Remove from unowned list if it’s in there
        unownedProperties.remove(property);

        System.out.println("Starting auction for " + property.getName() + " (Price: $" + property.getPrice() + ")");

        // Simple console-based auction logic (until we get the gameboard)
        Scanner scanner = new Scanner(System.in);
        int highestBid = 0;
        Player highestBidder = null;

        for (Player p : players) 
        {
            // Skip players who cannot bid at all
            if (p.getMoney() <= 0) 
            {
                continue;
            }

            System.out.println(p.getName() + ", enter your bid (0 to skip): ");
            int bid = 0;
            try 
            {
                bid = Integer.parseInt(scanner.nextLine());
            } 
            catch (NumberFormatException ignored) {}

            if (bid > highestBid && bid <= p.getMoney()) 
            {
                highestBid = bid;
                highestBidder = p;
            }
        }

        if (highestBidder != null) 
        {
            property.buy(highestBidder); // Deducts money, sets ownership in the Property class
            // If we want to override the property price to be the bid cost, we can by not using property.buy(...)
            // Other methods:
            //   highestBidder.decreaseMoney(highestBid);
            //   property.setOwner(highestBidder);
            System.out.println(highestBidder.getName() + " wins the auction for $" + highestBid + ".");
        } else {
            // No one bid
            System.out.println("No bids were placed. The property remains with the Bank.");
            unownedProperties.add(property);
        }
    }

    // --- MORTGAGE METHODS ---

    /**
     * Mortgages a property (pays the player half its price).
     * Player retains possession but no rent can be collected while mortgaged.
     */
    public void mortgageProperty(Property property, Player owner) 
    {
        if (property.getOwner() != owner) 
        {
            System.out.println("Cannot mortgage property you don't own!");
            return;
        }
        if (property.isMortgaged()) 
        {
            System.out.println(property.getName() + " is already mortgaged.");
            return;
        }
        property.mortgage(); // Typically sets mortgaged = true internally
        // property.mortgage() is in the Property class and already calls owner.increaseMoney(price/2).
        // We can discuss which class it should fall into.
        // (owner.increaseMoney(property.getPrice() / 2);)
    }

    /**
     * Lifts a mortgage from a property if the owner has enough money
     * (and charges the 10% interest).
     */
    public void unmortgageProperty(Property property, Player owner) 
    {
        if (property.getOwner() != owner) 
        {
            System.out.println("Cannot unmortgage property you don't own!");
            return;
        }
        if (!property.isMortgaged())
        {
            System.out.println(property.getName() + " is not mortgaged.");
            return;
        }
        property.unmortgage(); 
        // property.unmortgage() is in the Property class.
        // We can discuss which class it should fall into.
    }

    // --- HOUSES & HOTELS ---

    /**
     * Sells a house to the player for the property indicated.
     * We need to have logic to show the property is fully owned
     * (entire color group) and that the build envenly rule is being used.
     *
     */
    public void sellHouse(Player owner, Property property) 
    {
        
    }

    /**
     * Sells a hotel to the player for the property indicated.
     * You need 4 houses first, then you can upgrade to a hotel and must return those 4 houses.
     */
    public void sellHotel(Player owner, Property property) 
    {
        
    }

    /**
     * Buys back a house from the player at half the price.
     * Ex: if a house cost $100 to build, the Bank pays the player $50.
     */
    public void buyBackHouse(Player owner, Property property) 
    {
        
    }

    /**
     * Buys back a hotel from the player at half the hotel cost.
     */
    public void buyBackHotel(Player owner, Property property) 
    {

    }

    // --- UTILITY / DEBUG ---

    /**
     * Prints the current state of the Bank: how many houses/hotels remain,
     * and any unowned properties.
     */
    public void printBankStatus() 
    {
        System.out.println("\n--- Bank Status ---");
        System.out.println("Houses remaining: " + housesRemaining);
        System.out.println("Hotels remaining: " + hotelsRemaining);
        System.out.println("Unowned properties:");
        for (Property p : unownedProperties) 
        {
            System.out.println("   - " + p.getName() + " ($" + p.getPrice() + ")");
        }
        System.out.println("-------------------\n");
    }

}
