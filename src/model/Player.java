package model;

import java.util.List;

/**
 * Represents the user-controlled entity within the game world.
 * Manages spatial state (current location) and possession state (inventory).
 */
public interface Player {
    /**
     * Retrieves the player's current location in the game world.
     * @return The current Room instance.
     */
    Room getCurrentRoom();

    /**
     * Updates the player's spatial location.
     * @param room The new Room to relocate the player to.
     */
    void setCurrentRoom(Room room);

    /**
     * Retrieves the collection of items currently held by the player.
     * @return A list of Item objects within the inventory.
     */
    List<Item> getInventory();

    /**
     * Adds an item to the player's inventory state.
     * @param item The item to collect.
     */
    void addItem(Item item);

    /**
     * Removes an item from the player's inventory state.
     * @param item The item to discard or utilize.
     */
    void removeItem(Item item);

    /**
     * Evaluates whether the player possesses a specific item, typically used for condition checks.
     * @param itemName The nomenclature of the item to search for.
     * @return True if the item resides in the inventory, false otherwise.
     */
    boolean hasItem(String itemName);
}