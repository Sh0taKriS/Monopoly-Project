package Model;

import java.util.Random;

/**
 * Represents a pair of dice used in the game.
 */
public class Dice {
    private int result1;
    private int result2;
    private int doublesRolled;

    public Dice() {
        result1 = 0;
        result2 = 0;
        doublesRolled = 0;
    }

    /**
     * Rolls the dice for the specified player.
     *
     * @param player The player rolling the dice.
     */
    public void rollDice(Player player) {
        Random r = new Random();
        result1 = r.nextInt(1, 7);
        result2 = r.nextInt(1, 7);
        if (result1 == result2) {
            doublesRolled += 1;
            if (doublesRolled < 3) {
                player.move(result1 + result2);
                rollDice(player);
            } else {
                player.goToJail();
            }
        } else {
            doublesRolled = 0;
            player.move(result1 + result2);
        }
    }

    /**
     * Rolls the dice to determine if the player can get out of jail.
     *
     * @return True if the player rolls doubles, false otherwise.
     */
    public boolean rollJail() {
        Random r = new Random();
        result1 = r.nextInt(1, 7);
        result2 = r.nextInt(1, 7);
        return result1 == result2;
    }
}