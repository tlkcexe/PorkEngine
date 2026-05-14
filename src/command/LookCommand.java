package command;

import engine.GameState;
import model.Item;
import model.Room;
import ui.ConsoleUI;

/**
 * Command implementation for environmental observation.
 * Outputs the current room's narrative description and dynamically lists
 * all visible, interactable items currently located in the same spatial node.
 */
public class LookCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        
        ui.printRoomHeader(currentRoom.getName());
        ui.printMessage(currentRoom.getDescription());
        
        if (currentRoom.getItems() != null && !currentRoom.getItems().isEmpty()) {
            ui.printMessage("\nVisible items:");
            for (Item item : currentRoom.getItems()) {
                ui.printMessage("  ~ " + item.getName());
            }
        }
    }
}