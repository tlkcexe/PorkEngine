package command;

import engine.GameState;
import model.Item;
import model.Room;
import java.util.List;

public class DropCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState) {
        if (args.isEmpty()) {
            System.out.println("Drop what? (e.g., 'drop torch')");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentLocation();
        List<Item> inventory = gameState.getPlayer().getInventory();
        
        if (inventory != null) {
            for (Item item : inventory) {
                if (item.getName().toLowerCase().equals(itemName)) {
                    inventory.remove(item);
                    currentRoom.getItems().add(item);
                    System.out.println("You dropped the " + itemName + ".");
                    return;
                }
            }
        }
        System.out.println("You are not carrying a " + itemName + ".");
    }
}