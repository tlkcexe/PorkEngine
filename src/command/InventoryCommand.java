package command;

import engine.GameState;
import model.Item;
import ui.ConsoleUI;
import java.util.List;

/**
 * Command implementation for querying the player's current possessions.
 * Iterates through the encapsulated inventory list and outputs the state to the UI.
 */
public class InventoryCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        List<Item> inventory = gameState.getPlayer().getInventory();
        
        if (inventory == null || inventory.isEmpty()) {
            ui.printMessage("You are empty-handed.");
            return;
        }

        ui.printMessage("You are carrying:");
        for (Item item : inventory) {
            ui.printMessage("  - " + item.getName() + " (" + item.getDescription() + ")");
        }
    }
}