package command;

import engine.GameState;
import model.Item;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

public class DropCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState, ConsoleUI ui) {
        if (args.isEmpty()) {
            ui.printMessage("Drop what? (e.g., 'drop torch')");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        
        // Ψάχνουμε το αντικείμενο στο inventory του παίκτη
        Item itemToDrop = null;
        if (gameState.getPlayer().getInventory() != null) {
            for (Item item : gameState.getPlayer().getInventory()) {
                if (item.getName().toLowerCase().equals(itemName)) {
                    itemToDrop = item;
                    break;
                }
            }
        }
        
        if (itemToDrop != null) {
            gameState.getPlayer().removeItem(itemToDrop);
            currentRoom.addItem(itemToDrop);
            ui.printMessage("You dropped the " + itemToDrop.getName() + ".");
        } else {
            ui.printMessage("You are not carrying a '" + itemName + "'.");
        }
    }
}