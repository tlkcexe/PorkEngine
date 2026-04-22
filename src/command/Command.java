package command;

import engine.GameState;
import ui.ConsoleUI;

/**
 * The Strategy interface for all executable actions within the game.
 * Adhering to the Open/Closed Principle, new behaviors can be introduced 
 * by creating new classes that implement this interface, without modifying the engine core.
 */
public interface Command {
    /**
     * Executes the specific logic of the command.
     * @param command The syntactically parsed user input (DTO).
     * @param gameState The current state of the game session to evaluate or modify.
     * @param ui The user interface interface for outputting execution results.
     */
    void execute(ParsedCommand command, GameState gameState, ConsoleUI ui);
}