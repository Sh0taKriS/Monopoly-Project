/**
 * Class Created by Kristian Wright
 */
package Model;

/**
 * Represents a space on the Monopoly game board.
 * Each space has a name and a method to handle a player landing on it.
 */
public abstract class Space {
    protected String name;

    /**
     * Constructs a Space with the given name.
     *
     * @param name The name of the space.
     */
    public Space(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the space.
     *
     * @return The name of the space.
     */
    public String getName() {
        return name;
    }

    /**
     * Handles the event when a player lands on the space.
     *
     * @param player The player landing on the space.
     */
    public abstract void landOn(Player player);

    // GoSpace class
    /**
     * Represents the "Go" space on the board.
     * Players collect $200 when they land on or pass this space.
     */
    public static class GoSpace extends Space {
        public GoSpace() {
            super("Go");
        }

        @Override
        public void landOn(Player player) {
            player.increaseMoney(200); // Collect $200 for passing Go
            System.out.println(player.getName() + " landed on Go and collected $200.");
        }
    }

    // CommunityChestSpace class
    /**
     * Represents a Community Chest space on the board.
     * Players draw a Community Chest card when they land on this space.
     */
    public static class CommunityChestSpace extends Space {
        public CommunityChestSpace() {
            super("Community Chest");
        }

        @Override
        public void landOn(Player player) {
            GameBoard gameBoard = player.getGameBoard();
            CommunityChestCard card = gameBoard.getCommunityDeck().pop();
            System.out.println(player.getName() + " drew a Community Chest card: " + card.getDescription());
            card.apply(player);
            gameBoard.getCommunityDeck().push(card); // Optionally, put the card back at the bottom of the deck
        }
    }

    // IncomeTaxSpace class
    /**
     * Represents an Income Tax space on the board.
     * Players pay $200 when they land on this space.
     */
    public static class IncomeTaxSpace extends Space {
        public IncomeTaxSpace() {
            super("Income Tax");
        }

        @Override
        public void landOn(Player player) {
            player.decreaseMoney(200); // Pay $200 for Income Tax
            System.out.println(player.getName() + " landed on Income Tax and paid $200.");
        }
    }

    // RailroadSpace class
    /**
     * Represents a Railroad space on the board.
     * Players can buy the railroad or pay rent if it is owned by another player.
     */
    public static class RailroadSpace extends Space {
        final private int price;

        public RailroadSpace(String name, int location, int price, int costWithOne, int costWithTwo,
                             int costWithThree, int costWithFour, int mortgageValue) {
            super(name);
            this.price = price;
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Railroad space
            System.out.println(player.getName() + " landed on " + name + " Railroad.");
        }
    }

    // ChanceSpace class
    /**
     * Represents a Chance space on the board.
     * Players draw a Chance card when they land on this space.
     */
    public static class ChanceSpace extends Space {
        public ChanceSpace() {
            super("Chance");
        }

        @Override
        public void landOn(Player player) {
            GameBoard gameBoard = player.getGameBoard();
            ChanceCard card = gameBoard.getChanceDeck().pop();
            System.out.println(player.getName() + " drew a Chance card: " + card.getDescription());
            card.apply(player);
            gameBoard.getChanceDeck().push(card); // Optionally, put the card back at the bottom of the deck
        }
    }

    // JailSpace class
    /**
     * Represents a Jail space on the board.
     * Players are just visiting when they land on this space.
     */
    public static class JailSpace extends Space {
        public JailSpace() {
            super("Jail");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Jail space
            System.out.println(player.getName() + " landed on Jail.");
        }
    }

    // UtilitySpace class
    /**
     * Represents a Utility space on the board.
     * Players can buy the utility or pay rent if it is owned by another player.
     */
    public static class UtilitySpace extends Space {
        final private int price;

        public UtilitySpace(String name, int location, int price, int mortgageValue) {
            super(name);
            this.price = price;
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Utility space
            System.out.println(player.getName() + " landed on " + name + " Utility.");
        }
    }

    // PropertySpace class
    /**
     * Represents a Property space on the board.
     * Players can buy the property or pay rent if it is owned by another player.
     */
    public static class PropertySpace extends Space {
        final private int price;
        private Player owner;

        public PropertySpace(String name, int location, String color, int price,
                             int propertySite, int propertySiteWithColorSet,
                             int costWithOne, int costWithTwo, int costWithThree,
                             int costWithFour, int costWithHotel, int mortgageValue,
                             int costOfHouseHotel) {
            super(name);
            this.price = price;
            this.owner = null; // Initially unowned
        }

        @Override
        public void landOn(Player player) {
            if (owner == null) {
                // Property is unowned, player can buy it
                System.out.println(player.getName() + " landed on " + name + " which is unowned.");
            } else if (owner != player) {
                // Property is owned by another player, pay rent
                int rent = calculateRent();
                player.decreaseMoney(rent);
                owner.increaseMoney(rent);
                System.out.println(player.getName() + " landed on " + name + " and paid $" + rent + " rent to " + owner.getName());
            } else {
                // Property is owned by the player
                System.out.println(player.getName() + " landed on their own property " + name + ".");
            }
        }

        public int calculateRent() {
            // Example rent calculation logic
            return price / 10;
        }

        public void buy(Player player) {
            if (owner == null) {
                owner = player;
                player.decreaseMoney(price);
                System.out.println(player.getName() + " bought " + name + " for $" + price);
            }
        }

        public boolean isOwned() {
            return owner != null;
        }

        public Player getOwner() {
            return owner;
        }
    }

    // FreeParkingSpace class
    /**
     * Represents a Free Parking space on the board.
     * Players do not perform any action when they land on this space.
     */
    public static class FreeParkingSpace extends Space {
        public FreeParkingSpace() {
            super("Free Parking");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Free Parking space
            System.out.println(player.getName() + " landed on Free Parking.");
        }
    }

    // GoToJailSpace class
    /**
     * Represents a Go-To Jail space on the board.
     * Players are sent to jail when they land on this space.
     */
    public static class GoToJailSpace extends Space {
        public GoToJailSpace() {
            super("Go To Jail");
        }

        @Override
        public void landOn(Player player) {
            player.goToJail(); // Send player to Jail
            System.out.println(player.getName() + " landed on Go To Jail and is sent to Jail.");
        }
    }

    // LuxuryTaxSpace class
    /**
     * Represents a Luxury Tax space on the board.
     * Players pay $75 when they land on this space.
     */
    public static class LuxuryTaxSpace extends Space {
        public LuxuryTaxSpace() {
            super("Luxury Tax");
        }

        @Override
        public void landOn(Player player) {
            player.decreaseMoney(75); // Pay $75 for Luxury Tax
            System.out.println(player.getName() + " landed on Luxury Tax and paid $75.");
        }
    }

    // TaxSpace class
    /**
     * Represents a Tax space on the board.
     * Players pay a specified amount of tax when they land on this space.
     */
    public static class TaxSpace extends Space {
        private final int taxAmount;

        public TaxSpace(String name, int location, int taxAmount) {
            super(name);
            this.taxAmount = taxAmount;
        }

        @Override
        public void landOn(Player player) {
            player.decreaseMoney(taxAmount);
            System.out.println(player.getName() + " landed on " + name + " and paid $" + taxAmount + ".");
        }
    }
}