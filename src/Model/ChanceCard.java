/**
 * Class Created by Kristian Wright
 */
package Model;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents a Chance card in the game.
 * Each Chance card has a description and an effect that is applied to a player.
 */
public class ChanceCard {
    final private String description;
    final private Consumer<Player> effect;

    /**
     * Constructs a ChanceCard with the given description and effect.
     *
     * @param description The description of the Chance card.
     * @param effect The effect of the Chance card, represented as a Consumer of Player.
     */
    public ChanceCard(String description, Consumer<Player> effect) {
        this.description = description;
        this.effect = effect;
    }

    /**
     * Applies the effect of the Chance card to the given player.
     *
     * @param player The player to apply the effect to.
     */
    public void apply(Player player) {
        effect.accept(player);
    }

    /**
     * Gets the description of the Chance card.
     *
     * @return The description of the Chance card.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChanceCard that = (ChanceCard) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    // Helper methods for card actions

    /**
     * Moves the player to the nearest utility.
     */
    public static void moveToNearestUtility() {
        // Logic to move player to nearest utility
    }

    /**
     * Moves the player to the nearest railroad.
     */
    public static void moveToNearestRailroad() {
        // Logic to move player to nearest railroad
    }

    /**
     * Makes general repairs on the player's properties.
     */
    public static void makeGeneralRepairs() {
        // Logic to make general repairs on player's properties
    }

    /**
     * Pays each player a specified amount.
     */
    public static void payEachPlayer() {
        // Logic to pay each player a specified amount
    }
}