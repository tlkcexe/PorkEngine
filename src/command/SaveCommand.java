package command;

import engine.GameState;
import ui.ConsoleUI;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Persists the current game state (e.g., room and difficulty) to an external 
 * text file. Allows players to save progress and return later.
 */
public class SaveCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        try (FileWriter writer = new FileWriter("savegame.txt")) {
            // Write core state data to file
            writer.write(gameState.getPlayer().getCurrentRoom().getId() + "\n");
            writer.write(gameState.getDifficultyLevel() + "\n");
            ui.printMessage("Game progress saved successfully.");
        } catch (IOException e) {
            ui.printMessage("Failed to save game. Please check file permissions.");
        }
    }
}