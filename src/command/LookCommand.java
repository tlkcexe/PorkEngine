package command;

import engine.GameState;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

public class LookCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState, ConsoleUI ui) {
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        
        ui.printMessage("--- " + currentRoom.getName() + " ---");
        ui.printMessage(currentRoom.getDescription());
        
        if (currentRoom.getItems() != null && !currentRoom.getItems().isEmpty()) {
            StringBuilder itemsStr = new StringBuilder("You see the following items here: ");
            for (int i = 0; i < currentRoom.getItems().size(); i++) {
                itemsStr.append(currentRoom.getItems().get(i).getName());
                if (i < currentRoom.getItems().size() - 1) {
                    itemsStr.append(", ");
                }
            }
            ui.printMessage(itemsStr.toString());
        }
    }
}