package command;

import engine.GameState;
import model.Exit;
import model.Room;
import java.util.List;

public class GoCommand implements Command {
    @Override
    public void execute(List<String> args, GameState gameState) {
        if (args.isEmpty()) {
            System.out.println("Go where? (e.g., 'go north')");
            return;
        }

        String direction = args.get(0).toLowerCase();
        Room currentRoom = gameState.getPlayer().getCurrentLocation();
        
        for (Exit exit : currentRoom.getExits()) {
            // Υποθέτουμε ότι το Exit έχει getDirection(). Αν όχι, θα το φτιάξει ο Αλέξανδρος.
            if (exit.getDirection().equals(direction)) {
                if (exit.isLocked()) {
                    System.out.println("The door to the " + direction + " is locked.");
                } else {
                    gameState.getPlayer().setCurrentLocation(exit.getTargetRoom());
                    System.out.println("You move " + direction + ".");
                    
                    Room newRoom = gameState.getPlayer().getCurrentLocation();
                    System.out.println("\n--- " + newRoom.getName() + " ---");
                    System.out.println(newRoom.getDescription());
                }
                return;
            }
        }
        System.out.println("You can't go that way.");
    }
}