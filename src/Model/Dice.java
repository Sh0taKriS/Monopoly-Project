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
        System.out.println("Rolled: " + result1 + " and " + result2); // Print dice roll

        if (result1 == result2) {
            doublesRolled += 1;
            System.out.println(player.getToken() + " rolled doubles and gets another turn!");
            if (doublesRolled < 3) {
                player.move(result1 + result2);
                rollDice(player); // Recurse if doubles rolled
            } else {
                player.goToJail(); // Send to jail after 3 doubles
            }
        } else {
            doublesRolled = 0;
            player.move(result1 + result2); // Move player
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

    // Getter for total dice roll
    public int getTotal() {
        return result1 + result2;
    }

    public int getResult1() {
        return result1;
    }

    public int getResult2() {
        return result2;
    }
}