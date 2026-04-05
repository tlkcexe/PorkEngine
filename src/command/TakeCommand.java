package command;

import engine.GameState;
import model.Item;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

public class TakeCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState, ConsoleUI ui) {
        if (args.isEmpty()) {
            ui.printMessage("Take what? (e.g., 'take key')");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        
        // Ψάχνουμε αν το αντικείμενο υπάρχει στο δωμάτιο
        Item itemToTake = currentRoom.getItemByName(itemName);
        
        if (itemToTake != null) {
            currentRoom.removeItem(itemToTake);
            gameState.getPlayer().addItem(itemToTake);
            ui.printMessage("You picked up the " + itemToTake.getName() + ".");
        } else {
            ui.printMessage("You don't see a '" + itemName + "' here.");
        }
    }
}