package View;

import Model.GameBoard;
import Model.Player;

import java.util.ArrayList;
import java.util.List;

public class NewMonopolyGame {
    public static void main(String[] args) {
        // Create a list of players
        List<Player> players = new ArrayList<>();
        GameBoard board = new GameBoard(players); // Initialize GameBoard first

        players.add(new Player("Player 1", "Token 1", board));
        players.add(new Player("Player 2", "Token 2", board));
        // Add more players as needed

        // Initialize game components with the list of players
        /*
        As of right now this is not needed but is a placeholder for future use

        Bank bank = new Bank();
        Game game = new Game(board, bank);

        // Setup the game (shuffle cards, distribute money, assign tokens)
        game.setupGame();

        // Start the game loop
        game.start();

         */
    }
}