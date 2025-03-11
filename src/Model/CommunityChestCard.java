/**
 * Class Created by Kristian Wright
 */
package Model;

import java.util.function.Consumer;

/**
 * Represents a Community Chest card in the game.
 * Each Community Chest card has a description and an effect that is applied to a player.
 */
public class CommunityChestCard {
    public static Consumer<Player> assessStreetRepairs;
    final private String description;
    final private Consumer<Player> effect;

    /**
     * Constructs a CommunityChestCard with the given description and effect.
     *
     * @param description The description of the Community Chest card.
     * @param effect The effect of the Community Chest card, represented as a Consumer of Player.
     */
    public CommunityChestCard(String description, Consumer<Player> effect) {
        this.description = description;
        this.effect = effect;
    }

    /**
     * Applies the effect of the Community Chest card to the given player.
     *
     * @param player The player to apply the effect to.
     */
    public void apply(Player player) {
        effect.accept(player);
    }

    /**
     * Gets the description of the Community Chest card.
     *
     * @return The description of the Community Chest card.
     */
    public String getDescription() {
        return description;
    }

    // Helper methods for card actions

    /**
     * Collects a specified amount from each player.
     */
    public static void collectFromEachPlayer() {
        // Logic to collect a specified amount from each player
    }
}