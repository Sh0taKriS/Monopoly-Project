package Model;

import java.util.function.Consumer;

public class ChanceCard {
    final private String description;
    final private Consumer<Player> effect;

    public ChanceCard(String description, Consumer<Player> effect) {
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
    public static void moveToNearestUtility(Player player) {
        // Logic to move player to nearest utility
    }

    public static void moveToNearestRailroad(Player player) {
        // Logic to move player to nearest railroad
    }

    public static void makeGeneralRepairs(Player player) {
        // Logic to make general repairs on player's properties
    }

    public static void payEachPlayer(Player player, int amount) {
        // Logic to pay each player a specified amount
    }

    public static void collectFromEachPlayer(Player player, int amount) {
        // Logic to collect a specified amount from each player
    }

    public static Consumer<Player> getEffect(String description) {
        return player -> {
            switch (description) {
                case "Advance to Boardwalk.":
                    player.setPosition(39);
                    break;
                case "Advance to Go (Collect $200).":
                    player.setPosition(0);
                    player.increaseMoney(200);
                    break;
                case "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200.":
                    player.setPosition(10);
                    break;
                case "Pay school fees of $150.":
                    player.decreaseMoney(150);
                    break;
                case "Bank pays you dividend of $50.":
                    player.increaseMoney(50);
                    break;
                case "Your building loan matures. Collect $150.":
                    player.increaseMoney(150);
                    break;
                case "You have won a crossword competition. Collect $100.":
                    player.increaseMoney(100);
                    break;
                case "Advance to Illinois Avenue. If you pass Go, collect $200.":
                    if (player.getPosition() > 24) {
                        player.increaseMoney(200);
                    }
                    player.setPosition(24);
                    break;
                case "Advance to St. Charles Place. If you pass Go, collect $200.":
                    if (player.getPosition() > 11) {
                        player.increaseMoney(200);
                    }
                    player.setPosition(11);
                    break;
                case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled.":
                    moveToNearestRailroad(player);
                    break;
                case "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.":
                    moveToNearestUtility(player);
                    break;
                case "Take a trip to Reading Railroad. If you pass Go, collect $200.":
                    if (player.getPosition() > 5) {
                        player.increaseMoney(200);
                    }
                    player.setPosition(5);
                    break;
                case "Speeding fine $15.":
                    player.decreaseMoney(15);
                    break;
                case "Go back 3 spaces.":
                    player.setPosition(player.getPosition() - 3);
                    break;
                case "Make general repairs on all your property. For each house pay $25. For each hotel pay $100.":
                    makeGeneralRepairs(player);
                    break;
                case "Pay poor tax of $15.":
                    player.decreaseMoney(15);
                    break;
                case "You have been elected Chairman of the Board. Pay each player $50.":
                    payEachPlayer(player, 50);
                    break;
                case "Get Out of Jail Free.":
                    player.receiveGetOutOfJailFreeCard();
                    break;
                default:
                    System.out.println("Unknown Chance card: " + description);
            }
        };
    }
}