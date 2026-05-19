package command;

import engine.GameState;
import ui.ConsoleUI;
import java.util.List;

/**
 * Retrieves and displays the history of commands entered by the player 
 * during the current session, fulfilling the command history requirement.
 */
public class HistoryCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        List<String> history = gameState.getCommandHistory();
        
        if (history.isEmpty()) {
            ui.printMessage("No commands have been entered yet.");
            return;
        }
        
        // Print all previously executed valid commands
        ui.printMessage("Command History:");
        for (int i = 0; i < history.size(); i++) {
            ui.printMessage((i + 1) + ". " + history.get(i));
        }
    }
}