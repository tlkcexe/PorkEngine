package command;

import engine.GameState;
import model.Item;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

// Εντολή για να επιθεωρήσεις ένα αντικείμενο ή το δωμάτιο.
public class InspectCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState, ConsoleUI ui) {
        if (args.isEmpty()) {
            // Αν δεν δώσει όρισμα, δείξε το δωμάτιο
            Room currentRoom = gameState.getPlayer().getCurrentRoom();
            ui.printMessage("You inspect the room: " + currentRoom.getDescription());
            return;
        }

        String target = args.get(0).toLowerCase();

        // Πρώτα ελέγχουμε αν είναι αντικείμενο στο inventory
        Item item = gameState.getPlayer().hasItem(target);
        if (item != null) {
            ui.printMessage("You inspect the " + item.getName() + ": " + item.getDescription());
            return;
        }

        // Μετά ελέγχουμε αν είναι αντικείμενο στο δωμάτιο
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        item = currentRoom.getItemByName(target);
        if (item != null) {
            ui.printMessage("You inspect the " + item.getName() + ": " + item.getDescription());
            return;
        }

        ui.printMessage("You don't see a '" + target + "' to inspect.");
    }
}