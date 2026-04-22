package model;

import java.util.List;

/**
 * Represents a distinct location or node within the game's spatial graph.
 * A room acts as a container for interactable entities (Items) and 
 * provides navigational links (Exits) to other rooms.
 */
public interface Room {
    /** @return The unique internal identifier used for JSON mapping. */
    String getId();
    
    /** @return The display name of the room. */
    String getName();
    
    /** @return The descriptive narrative text of the room. */
    String getDescription();

    // --- Item Management ---
    List<Item> getItems();
    void addItem(Item item);
    void removeItem(Item item);
    Item getItemByName(String itemName);

    // --- Navigation Management ---
    List<Exit> getExits();
    void addExit(Exit exit);
    Exit getExitByDirection(String direction);
}