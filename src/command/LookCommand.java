package command;

import engine.GameState;
import model.Item;
import model.Room;
import java.util.List;

public class LookCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState) {
        Room currentRoom = gameState.getPlayer().getCurrentLocation();
        
        System.out.println("--- " + currentRoom.getName() + " ---");
        System.out.println(currentRoom.getDescription());
        
        if (currentRoom.getItems() != null && !currentRoom.getItems().isEmpty()) {
            System.out.print("You see the following items here: ");
            for (int i = 0; i < currentRoom.getItems().size(); i++) {
                System.out.print(currentRoom.getItems().get(i).getName());
                if (i < currentRoom.getItems().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}