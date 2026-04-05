package command;

import engine.GameState;
import model.Item;
import model.Room;
import java.util.List;

public class TakeCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState) {
        if (args.isEmpty()) {
            System.out.println("Take what? (e.g., 'take key')");
            return;
        }

        String itemName = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentLocation();
        
        if (currentRoom.getItems() != null) {
            for (Item item : currentRoom.getItems()) {
                if (item.getName().toLowerCase().equals(itemName)) {
                    currentRoom.getItems().remove(item);
                    gameState.getPlayer().getInventory().add(item);
                    System.out.println("You picked up the " + itemName + ".");
                    return;
                }
            }
        }
        System.out.println("You don't see a " + itemName + " here.");
    }
}