package command;

import engine.GameState;
import ui.ConsoleUI;

/**
 * Handles the termination of the game loop gracefully.
 * Replaces the hardcoded exit logic in the GameEngine to conform to the command pattern.
 */
public class QuitCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        // Trigger the engine's shutdown flag
        gameState.setGameOver(true);
        ui.printMessage("Goodbye! Thanks for playing.");
    }
}