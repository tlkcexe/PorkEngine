package command;

import engine.GameState;
import model.Exit;
import model.Item;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

// Εντολή για να χρησιμοποιήσεις ένα αντικείμενο σε μια κατεύθυνση ή πόρτα.
public class UseCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState, ConsoleUI ui) {
        if (args.size() < 2) {
            ui.printMessage("Use what on what? (e.g., 'use key north')");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        String target = args.get(1).toLowerCase();

        // Ελέγχουμε αν ο παίκτης έχει το αντικείμενο
        Item item = gameState.getPlayer().hasItem(itemName);
        if (item == null) {
            ui.printMessage("You don't have a '" + itemName + "'.");
            return;
        }

        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        Exit exit = currentRoom.getExitByDirection(target);

        if (exit != null && exit.isLocked()) {
            // Ελέγχουμε αν το αντικείμενο ταιριάζει με το required item
            if (item.getName().equalsIgnoreCase(exit.getRequiredItemId())) {
                exit.setLocked(false);
                ui.printMessage("You used the " + item.getName() + " to unlock the " + target + " door.");
            } else {
                ui.printMessage("The " + item.getName() + " doesn't work on the " + target + " door.");
            }
        } else {
            ui.printMessage("You can't use the " + item.getName() + " on '" + target + "'.");
        }
    }
}