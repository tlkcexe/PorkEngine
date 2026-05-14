package command;

import engine.GameState;
import model.Exit;
import ui.ConsoleUI;
import java.util.List;

/**
 * Command implementation for utilizing inventory objects.
 * Supports multi-object interactions (e.g., applying an item to an environmental target)
 * and evaluates conditional locks defined in the domain logic.
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

        // 1. Verify possession via the State manager
        if (!gameState.getPlayer().hasItem(itemToUse)) {
            ui.printMessage("You don't have a '" + itemToUse + "' in your inventory.");
            return;
        }

        // 2. Evaluate multi-target interactions (e.g., "use key on door")
        if (command.getPreposition() != null && command.getSecondArg() != null) {
            String target = command.getSecondArg().toLowerCase();
            
            // Query current node for constrained paths matching the target string
            Exit exit = gameState.getPlayer().getCurrentRoom().getExitByDirection(target);
            
            if (exit != null && exit.isLocked()) {
                // Dependency check: Does the item ID match the lock's requirement?
                if (itemToUse.equalsIgnoreCase(exit.getRequiredItemId())) {
                    exit.setLocked(false);
                    ui.printMessage("Click! The path to the " + target + " is now unlocked.");
                } else {
                    ui.printMessage("The " + itemToUse + " doesn't fit in that lock.");
                }
            } else {
                ui.printMessage("You can't use the " + itemToUse + " on that.");
            }
        } else {
            // Un-targeted item utilization fallback
            ui.printMessage("You used the " + itemToUse + ". Nothing special happens... yet.");
        }
    }
}