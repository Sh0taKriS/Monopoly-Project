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
     * Sells a house to the player for the given property.
     * The player must own the full color set and build houses evenly.
     */
    public void sellHouse(Player owner, Property property) {
        if (housesRemaining <= 0) {
            System.out.println("No more houses available in the Bank.");
            return;
        }
        if (property.getOwner() != owner) {
            System.out.println(owner.getName() + " does not own " + property.getName());
            return;
        }
        if (property.isMortgaged()) {
            System.out.println("Cannot build on mortgaged properties.");
            return;
        }

        // Verify the player owns the full color group
        List<Property> colorSet = getPropertiesInColorGroup(property);
        if (!owner.getProperties().containsAll(colorSet)) {
            System.out.println(owner.getName() + " must own the full color set to build houses.");
            return;
        }

        // Forces even building (find the minimum house count in the set)
        int minHouses = colorSet.stream().mapToInt(Property::getHouseCount).min().orElse(0);
        if (property.getHouseCount() > minHouses) {
            System.out.println("Houses must be built evenly across the color group.");
            return;
        }

        int houseCost = property.getPrice() / 2; // House cost formula
        if (owner.getMoney() < houseCost) {
            System.out.println(owner.getName() + " cannot afford a house on " + property.getName());
            return;
        }

        owner.decreaseMoney(houseCost);
        property.addHouse();
        housesRemaining--;

        System.out.println(owner.getName() + " built a house on " + property.getName() + " for $" + houseCost);
    }

    /**
     * Sells a hotel to the player for the given property.
     * Requires 4 houses first and returns them to the Bank.
     */
    public void sellHotel(Player owner, Property property) {
        if (hotelsRemaining <= 0) {
            System.out.println("No more hotels available in the Bank.");
            return;
        }
        if (property.getOwner() != owner) {
            System.out.println(owner.getName() + " does not own " + property.getName());
            return;
        }
        if (property.isMortgaged()) {
            System.out.println("Cannot build on mortgaged properties.");
            return;
        }

        if (property.getHouseCount() < 4) {
            System.out.println("Must have 4 houses before upgrading to a hotel.");
            return;
        }

        int hotelCost = property.getPrice(); // Example hotel cost formula
        if (owner.getMoney() < hotelCost) {
            System.out.println(owner.getName() + " cannot afford a hotel on " + property.getName());
            return;
        }

        // Remove 4 houses and add a hotel
        property.setHouseCount(0);
        property.setHasHotel(true);
        owner.decreaseMoney(hotelCost);
        housesRemaining += 4; // Return houses to the bank
        hotelsRemaining--;

        System.out.println(owner.getName() + " upgraded to a hotel on " + property.getName() + " for $" + hotelCost);
    }

    /**
     * Buys back a house from the player at half its purchase price.
     */
    public void buyBackHouse(Player owner, Property property) {
        if (property.getHouseCount() == 0) {
            System.out.println("No houses to sell back on " + property.getName());
            return;
        }

        int refundAmount = (property.getPrice() / 2) / 2; // Half of the original cost
        property.removeHouse();
        housesRemaining++;
        owner.increaseMoney(refundAmount);

        System.out.println("Bank bought back a house from " + owner.getName() + " for $" + refundAmount);
    }

    /**
     * Buys back a hotel from the player at half its purchase price.
     */
    public void buyBackHotel(Player owner, Property property) {
        if (!property.hasHotel()) {
            System.out.println("No hotel to sell back on " + property.getName());
            return;
        }

        int refundAmount = property.getPrice() / 2; // Half of original hotel cost
        property.setHasHotel(false);
        hotelsRemaining++;
        owner.increaseMoney(refundAmount);
        housesRemaining += 4; // Restore houses to the bank

        System.out.println("Bank bought back a hotel from " + owner.getName() + " for $" + refundAmount);
    }

    /**
     * Helper method to get all properties of the same color group.
     */
    private List<Property> getPropertiesInColorGroup(Property property) {
        List<Property> colorGroup = new ArrayList<>();
        for (Property p : unownedProperties) {
            if (p.getColorGroup().equals(property.getColorGroup())) {
                colorGroup.add(p);
            }
        }
        return colorGroup;
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
