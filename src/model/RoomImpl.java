package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the Room interface.
 * Encapsulates the state and contents of a specific game location.
 */
public class RoomImpl implements Room {
    // Core properties are final to ensure immutability post-instantiation
    private final String id;
    private final String name;
    private final String description;
    
    // Entity containers
    private final List<Item> items;
    private final List<Exit> exits;

    public RoomImpl(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.exits = new ArrayList<>();
    }

    @Override public String getId() { return id; }
    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public List<Item> getItems() { return items; }
    @Override public List<Exit> getExits() { return exits; }

    @Override
    public void addItem(Item item) { 
        items.add(item); 
    }

    @Override
    public void removeItem(Item item) { 
        items.remove(item); 
    }

    @Override
    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) return item;
        }
        return null;
    }

    @Override
    public void addExit(Exit exit) { 
        exits.add(exit); 
    }

    @Override
    public Exit getExitByDirection(String direction) {
        for (Exit exit : exits) {
            if (exit.getDirection().equalsIgnoreCase(direction)) return exit;
        }
        return null;
    }
}