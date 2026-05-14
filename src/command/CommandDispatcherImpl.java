package command;

import engine.GameState;
import ui.ConsoleUI;

/**
 * Concrete implementation of the CommandDispatcher.
 * Acts as the Front Controller for user input, coordinating the Lexical Parser
 * and the Command Registry to resolve and execute behaviors dynamically.
 */
public class CommandDispatcherImpl implements CommandDispatcher {
    private CommandParser parser;
    private CommandRegistry registry;

    public CommandDispatcherImpl(CommandParser parser, CommandRegistry registry) {
        this.parser = parser;
        this.registry = registry;
    }

    @Override
    public void dispatch(String input, GameState gameState, ConsoleUI ui) {
        // 1. Delegate lexical analysis to the Parser
        ParsedCommand parsed = parser.tokenize(input);
        
        if (parsed == null) {
            ui.printMessage("Please enter a valid command.");
            return;
        }

        // 2. Resolve the abstract command from the active memory registry
        Command command = registry.getCommand(parsed.getVerb());

        // 3. Execute dynamically if mapped, otherwise invoke fallback response
        if (command != null) {
            command.execute(parsed, gameState, ui);
        } else {
            ui.printMessage("I don't know how to '" + parsed.getVerb() + "'.");
        }
    }
}