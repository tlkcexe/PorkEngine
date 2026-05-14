package command;

import engine.GameState;
import model.Item;
import ui.ConsoleUI;
import java.util.List;

/**
 * Command implementation for removing items from the player's possession.
 * Transfers the specified entity from the Player's inventory array back 
 * into the current Room's item array, persisting it in the environment.
 */
public class DropCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        List<String> args = command.getArgs();

        if (args.isEmpty()) {
            ui.printMessage("Drop what?");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        
        if (!gameState.getPlayer().hasItem(itemName)) {
            ui.printMessage("You don't have a " + itemName + ".");
            return;
        }

        Item itemToDrop = null;
        for (Item item : gameState.getPlayer().getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToDrop = item;
                break;
            }
        }

        if (itemToDrop != null) {
            gameState.getPlayer().removeItem(itemToDrop);
            gameState.getPlayer().getCurrentRoom().addItem(itemToDrop);
            ui.printMessage("You dropped the " + itemToDrop.getName() + ".");
        }
    }
}