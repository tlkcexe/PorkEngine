package command;

import engine.GameState;
import model.Item;
import model.Room;
import ui.ConsoleUI;
import java.util.List;

/**
 * Command implementation for transferring objects from the environment (Room) 
 * into the user's possession (Player Inventory).
 */
public class TakeCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) { 
        List<String> args = command.getArgs(); 
        
        if (args.isEmpty()) {
            ui.printMessage("Take what?");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentRoom();
        Item itemToTake = currentRoom.getItemByName(itemName);

        if (itemToTake != null) {
            currentRoom.removeItem(itemToTake);
            gameState.getPlayer().addItem(itemToTake);
            ui.printMessage("You picked up the " + itemToTake.getName() + ".");
        } else {
            ui.printMessage("There is no " + itemName + " here.");
        }
    }
}