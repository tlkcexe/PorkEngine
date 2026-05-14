package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the Player interface.
 * Maintains the volatile state of the user's session, including current coordinates
 * within the room graph and the collection of acquired items.
 */
public class PlayerImpl implements Player {
    private Room currentRoom;
    private final List<Item> inventory;

    public PlayerImpl() {
        this.inventory = new ArrayList<>();
    }

    @Override
    public Room getCurrentRoom() { 
        return currentRoom; 
    }

    @Override
    public void setCurrentRoom(Room room) { 
        this.currentRoom = room; 
    }

    @Override
    public List<Item> getInventory() { 
        return inventory; 
    }

    @Override
    public void addItem(Item item) { 
        inventory.add(item); 
    }

    @Override
    public void removeItem(Item item) { 
        inventory.remove(item); 
    }

    @Override
    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) return true;
        }
        return false;
    }
}