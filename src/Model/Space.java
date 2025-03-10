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
            // Logic for drawing a Community Chest card
            System.out.println(player.getName() + " landed on Community Chest.");
        }
    }

    // IncomeTaxSpace class
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

    // Railroad class
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
    public static class ChanceSpace extends Space {
        public ChanceSpace() {
            super("Chance");
        }

        @Override
        public void landOn(Player player) {
            // Logic for drawing a Chance card
            System.out.println(player.getName() + " landed on Chance.");
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

    // Utility class
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

     public static class PropertySpace extends Space {
        final private int price;

         public PropertySpace(String name, int location, String color, int price,
                              int propertySite, int propertySiteWithColorSet,
                              int costWithOne, int costWithTwo, int costWithThree,
                              int costWithFour, int costWithHotel, int mortgageValue,
                              int costOfHouseHotel) {
         super(name);
         this.price = price;
         }

         @Override
         public void landOn(Player player) {
             // Logic for landing on Property space
             System.out.println(player.getName() + " landed on " + name + " Property.");
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