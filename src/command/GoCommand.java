package command;

import engine.GameState;
import model.Exit;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

/**
 * Command implementation for spatial navigation.
 * Resolves directional input against the current room's valid exits,
 * evaluating lock constraints before updating the player's location.
 */
public class GoCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        List<String> args = command.getArgs();
        
        if (args.isEmpty()) {
            ui.printMessage("Go where? (e.g., 'go north')");
            return;
        }

        String direction = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        
        Exit exit = currentRoom.getExitByDirection(direction);
        
        if (exit != null) {
            if (exit.isLocked()) {
                ui.printMessage("The door to the " + direction + " is locked.");
            } else {
                String targetId = exit.getTargetRoomId();
                ui.printMessage("You move " + direction + "...");
                
                Room nextRoom = gameState.getRoomById(targetId);
                
                if (nextRoom != null) {
                    gameState.getPlayer().setCurrentRoom(nextRoom);
                    ui.printRoomHeader(nextRoom.getName());
                    ui.printMessage(nextRoom.getDescription());
                } else {
                    ui.printError("[CRITICAL ERROR] Spatial link broken. Could not resolve room ID: " + targetId);
                }
            }
        } else {
            ui.printMessage("You can't go that way.");
        }
    }
}