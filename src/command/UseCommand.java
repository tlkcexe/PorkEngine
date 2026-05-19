package command;

import engine.GameState;
import model.Exit;
import ui.ConsoleUI;
import java.util.List;

/**
 * Smart Command implementation for utilizing inventory objects.
 * Automatically scans the room to find a locked exit that matches the used item.
 */
public class UseCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        List<String> args = command.getArgs();
        
        if (args.isEmpty()) {
            ui.printMessage("Use what?");
            return;
        }

        String itemToUse = args.get(0).toLowerCase();

        // 1. Check if the player actually possesses the item in their inventory
        if (!gameState.getPlayer().hasItem(itemToUse)) {
            ui.printMessage("You don't have a '" + itemToUse + "' in your inventory.");
            return;
        }

        boolean usedSuccessfully = false;

        // 2. SMART CHECK: Iterate through all exits in the current room automatically
        for (Exit exit : gameState.getPlayer().getCurrentRoom().getExits()) {
            // Check if the exit is locked and the item matches the required key/item
            if (exit.isLocked() && itemToUse.equalsIgnoreCase(exit.getRequiredItemId())) {
                exit.setLocked(false); // Unlock the path
                ui.printMessage("Click! The door to the " + exit.getDirection() + " is now unlocked!");
                usedSuccessfully = true;
                break; // Target found and unlocked, stop searching
            }
        }

        // 3. Fallback: If the item doesn't fit any locked doors in the current room
        if (!usedSuccessfully) {
            ui.printMessage("You used the " + itemToUse + ", but nothing happened.");
        }
    }
}