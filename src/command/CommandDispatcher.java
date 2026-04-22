package command;

import engine.GameState;
import ui.ConsoleUI;

/**
 * Defines the contract for routing raw user input to the appropriate command handlers.
 * It acts as the central mediator between the UI (input), the Parser (lexical analysis),
 * and the Registry (command resolution).
 */
public interface CommandDispatcher {
    /**
     * Orchestrates the parsing and execution flow for a given input string.
     * @param input The raw text provided by the user.
     * @param gameState The active game environment state.
     * @param ui The output interface to render results.
     */
    void dispatch(String input, GameState gameState, ConsoleUI ui);
}