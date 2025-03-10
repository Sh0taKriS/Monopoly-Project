package Model;

import java.util.function.Consumer;

public class CommunityChestCard {
    final private String description;
    final private Consumer<Player> effect;

    public CommunityChestCard(String description, Consumer<Player> effect) {
        this.description = description;
        this.effect = effect;
    }

    public void apply(Player player) {
        effect.accept(player);
    }

    public String getDescription() {
        return description;
    }

    // Helper methods for card actions
    public static void collectFromEachPlayer(Player player, int amount) {
        // Logic to collect a specified amount from each player
    }

    public static void assessStreetRepairs(Player player) {
        // Logic to assess street repairs on player's properties
    }

    public static Consumer<Player> getEffect(String description) {
        return player -> {
            switch (description) {
                case "Advance to Go (Collect $200).":
                    player.setPosition(0);
                    player.increaseMoney(200);
                    break;
                case "Bank error in your favor. Collect $200.":
                    player.increaseMoney(200);
                    break;
                case "Doctor’s fee. Pay $50.":
                    player.decreaseMoney(50);
                    break;
                case "From sale of stock you get $50.":
                    player.increaseMoney(50);
                    break;
                case "Get Out of Jail Free.":
                    player.receiveGetOutOfJailFreeCard();
                    break;
                case "Go to Jail. Go directly to jail, do not pass Go, do not collect $200.":
                    player.setPosition(10);
                    break;
                case "Holiday fund matures. Receive $100.":
                    player.increaseMoney(100);
                    break;
                case "Income tax refund. Collect $20.":
                    player.increaseMoney(20);
                    break;
                case "It is your birthday. Collect $10 from every player.":
                    collectFromEachPlayer(player, 10);
                    break;
                case "Life insurance matures. Collect $100.":
                    player.increaseMoney(100);
                    break;
                case "Pay hospital fees of $100.":
                    player.decreaseMoney(100);
                    break;
                case "Pay school fees of $50.":
                    player.decreaseMoney(50);
                    break;
                case "Receive $25 consultancy fee.":
                    player.increaseMoney(25);
                    break;
                case "You are assessed for street repair. $40 per house. $115 per hotel.":
                    assessStreetRepairs(player);
                    break;
                case "You have won second prize in a beauty contest. Collect $10.":
                    player.increaseMoney(10);
                    break;
                case "You inherit $100.":
                    player.increaseMoney(100);
                    break;
                default:
                    System.out.println("Unknown Community Chest card: " + description);
            }
        };
    }
}