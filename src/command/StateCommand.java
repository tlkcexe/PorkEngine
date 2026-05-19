package command;

import engine.GameState;
import ui.ConsoleUI;

/**
 * Provides information about the current state of the game.
 * It can display general state, specific goals, or delegate to history based on the arguments.
 */
public class StateCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        String target = command.getFirstArg();
        
        // Default behavior: show location and difficulty
        if (target == null) {
            ui.printMessage("State: You are in " + gameState.getPlayer().getCurrentRoom().getName());
            ui.printMessage("Difficulty: " + gameState.getDifficultyLevel());
            return;
        }
        
        // Handle specific state requests
        if (target.equalsIgnoreCase("goal")) {
            ui.printMessage("Current Goal: " + gameState.getCurrentGoal());
        } else if (target.equalsIgnoreCase("history")) {
            Command historyCmd = new HistoryCommand();
            historyCmd.execute(command, gameState, ui);
        } else {
            ui.printMessage("State: You are in " + gameState.getPlayer().getCurrentRoom().getName());
        }
    }
}