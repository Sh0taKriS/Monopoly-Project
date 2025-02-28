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
            // Logic for landing on Go space
        }
    }

    // CommunityChestSpace class
    public static class CommunityChestSpace extends Space {
        public CommunityChestSpace() {
            super("Community Chest");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Community Chest space
        }
    }

    // IncomeTaxSpace class
    public static class IncomeTaxSpace extends Space {
        public IncomeTaxSpace() {
            super("Income Tax");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Income Tax space
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
        }
    }

    // ChanceSpace class
    public static class ChanceSpace extends Space {
        public ChanceSpace() {
            super("Chance");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Chance space
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
        }
    }

    // GoToJailSpace class
    public static class GoToJailSpace extends Space {
        public GoToJailSpace() {
            super("Go To Jail");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Go To Jail space
        }
    }

    // LuxuryTaxSpace class
    public static class LuxuryTaxSpace extends Space {
        public LuxuryTaxSpace() {
            super("Luxury Tax");
        }

        @Override
        public void landOn(Player player) {
            // Logic for landing on Luxury Tax space
        }
    }
}