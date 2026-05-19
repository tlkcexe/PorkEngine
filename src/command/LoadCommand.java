package command;

import engine.GameState;
import ui.ConsoleUI;
import model.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Restores a previously saved game state from an external text file,
 * updating the player's progress and parameters in the GameState.
 */
public class LoadCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        try (BufferedReader reader = new BufferedReader(new FileReader("savegame.txt"))) {
            // Read core state data from file
            String roomId = reader.readLine();
            String difficulty = reader.readLine();
            
            Room room = gameState.getRoomById(roomId);
            
            // Apply loaded data if valid
            if (room != null) {
                gameState.getPlayer().setCurrentRoom(room);
                gameState.setDifficultyLevel(difficulty);
                ui.printMessage("Game loaded successfully.");
                ui.printRoomHeader(room.getName());
            } else {
                ui.printMessage("Error: Could not locate the saved room.");
            }
        } catch (IOException e) {
            ui.printMessage("No save file found or failed to load.");
        }
    }
}