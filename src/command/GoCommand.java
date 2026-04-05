package command;

import engine.GameState;
import model.Exit;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

public class GoCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState, ConsoleUI ui) {
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
                
                // TODO: Εδώ χρειαζόμαστε από την ομάδα το εξής:
                // Room nextRoom = gameState.getRoomById(targetId);
                // gameState.getPlayer().setCurrentRoom(nextRoom);
                // ui.printMessage(nextRoom.getDescription());
                
                ui.printError("[SYSTEM] Need GameState.getRoomById() to complete movement to: " + targetId);
            }
        } else {
            ui.printMessage("You can't go that way.");
        }
    }
}