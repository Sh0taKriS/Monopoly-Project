public class Property extends Space {
    final private String name;
    final private int price;
    final private int baseRent;
    final private String colorGroup;
    private Player owner;
    private boolean mortgaged;
    final private int houseCount;
    final private boolean hasHotel;

    public Property(String name, int price, String colorGroup) {
        super(name); // Assuming Space has a constructor that takes a name
        this.name = name;
        this.price = price;
        this.baseRent = calculateBaseRent(price);
        this.colorGroup = colorGroup;
        this.owner = null; // Initially unowned
        this.mortgaged = false;
        this.houseCount = 0;
        this.hasHotel = false;
    }

    private int calculateBaseRent(int price) {
        return price / 10; // Example: Rent is 10% of the property price
    }

    public void buy(Player player) {
        if (this.owner == null) {
            this.owner = player;
            player.decreaseMoney(this.price);
            player.addProperty(this); // Ensure Player class has this method
            System.out.println(player.getName() + " bought " + this.name);
        } else {
            System.out.println(this.name + " is already owned.");
        }
    }

    public void payRent(Player player) {
        if (this.owner != null && this.owner != player && !this.mortgaged) {
            int rentAmount = calculateRent();
            player.decreaseMoney(rentAmount);
            this.owner.increaseMoney(rentAmount);
            System.out.println(player.getName() + " paid $" + rentAmount + " rent to " + this.owner.getName());
        }
    }

    public int calculateRent() {
        if (hasHotel) return baseRent * 50;
        if (houseCount > 0) return baseRent * (houseCount * 5);
        return baseRent;
    }

    public void mortgage() {
        if (!mortgaged) {
            mortgaged = true;
            owner.increaseMoney(price / 2);
            System.out.println(owner.getName() + " mortgaged " + this.name);
        }
    }

    public void unmortgage() {
        if (mortgaged) {
            mortgaged = false;
            owner.decreaseMoney((int) (price * 0.55)); // 10% interest
            System.out.println(owner.getName() + " unmortgaged " + this.name);
        }
    }

    public boolean isOwned() {
        return owner != null;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return this.price;
    }

    public boolean isMortgaged() {
        return this.mortgaged;
    }

    @Override
    public void landOn(Player player) {

    }
}