package Model;

public abstract class Space {
    protected String name;

    public Space(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void landOn(Player player);

    // GoSpace class
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

    // TaxSpace class
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

        public int getTaxAmount() {
            return taxAmount;
        }
    }

    // IncomeTaxSpace class
    public static class IncomeTaxSpace extends TaxSpace {
        public IncomeTaxSpace() {
            super("Income Tax", 4, 200);
        }
    }

    // RailroadSpace class
    public static class RailroadSpace extends Space {
        private final int price;
        private Player owner;

        public RailroadSpace(String name, int location, int price, int costWithOne, int costWithTwo,
                             int costWithThree, int costWithFour, int mortgageValue) {
            super(name);
            this.price = price;
            this.owner = null; // Initially unowned
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Railroad space
            System.out.println(player.getName() + " landed on " + name + " Railroad.");
        }

        public int getPrice() {
            return price;
        }

        public boolean isOwned() {
            return owner != null;
        }

        public Player getOwner() {
            return owner;
        }
    }

    // ChanceSpace class
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
    public static class UtilitySpace extends Space {
        private final int price;
        private Player owner;

        public UtilitySpace(String name, int location, int price, int mortgageValue) {
            super(name);
            this.price = price;
            this.owner = null; // Initially unowned
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Utility space
            System.out.println(player.getName() + " landed on " + name + " Utility.");
        }

        public int getPrice() {
            return price;
        }

        public boolean isOwned() {
            return owner != null;
        }

        public Player getOwner() {
            return owner;
        }
    }

    // PropertySpace class
    public static class PropertySpace extends Space {
        private final int price;
        private final int propertySite;
        private final int propertySiteWithColorSet;
        private final int costWithOneHouse;
        private final int costWithTwoHouses;
        private final int costWithThreeHouses;
        private final int costWithFourHouses;
        private final int costWithHotel;
        private final int mortgageValue;
        private final int costOfHouseHotel;
        private Player owner;

        public PropertySpace(String name, int location, String color, int price, int propertySite, int propertySiteWithColorSet,
                             int costWithOneHouse, int costWithTwoHouses, int costWithThreeHouses, int costWithFourHouses,
                             int costWithHotel, int mortgageValue, int costOfHouseHotel) {
            super(name);
            this.price = price;
            this.propertySite = propertySite;
            this.propertySiteWithColorSet = propertySiteWithColorSet;
            this.costWithOneHouse = costWithOneHouse;
            this.costWithTwoHouses = costWithTwoHouses;
            this.costWithThreeHouses = costWithThreeHouses;
            this.costWithFourHouses = costWithFourHouses;
            this.costWithHotel = costWithHotel;
            this.mortgageValue = mortgageValue;
            this.costOfHouseHotel = costOfHouseHotel;
            this.owner = null; // Initially unowned
        }

        @Override
        public void landOn(Player player) {
            if (owner == null) {
                System.out.println(player.getName() + " landed on " + name + " which is unowned.");
            } else if (owner != player) {
                int rent = calculateRent();
                player.decreaseMoney(rent);
                owner.increaseMoney(rent);
                System.out.println(player.getName() + " landed on " + name + " and paid $" + rent + " rent to " + owner.getName());
            } else {
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

        public int getPrice() {
            return price;
        }
    }

    // FreeParkingSpace class
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
}