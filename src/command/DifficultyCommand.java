package command;

import engine.GameState;
import ui.ConsoleUI;

/**
 * Manages the game's difficulty level, allowing the player to view 
 * or change it dynamically during gameplay.
 */
public class DifficultyCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        // If no argument is provided, display the current difficulty
        if (command.getArgs().isEmpty()) {
            ui.printMessage("Current difficulty is: " + gameState.getDifficultyLevel());
            ui.printMessage("To change, use: difficulty easy/normal/hard");
            return;
        }
        
        String level = command.getFirstArg().toLowerCase();
        
        // Validate and update the difficulty state
        if (level.equals("easy") || level.equals("normal") || level.equals("hard")) {
            gameState.setDifficultyLevel(level);
            ui.printMessage("Difficulty set to " + level + ".");
        } else {
            ui.printMessage("Invalid difficulty level. Choose easy, normal, or hard.");
        }
    }
}