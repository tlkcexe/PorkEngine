package model;

/**
 * Represents a generic interactable object within the game world.
 * This interface establishes the contract for all items, ensuring the engine
 * can handle them uniformly without coupling to specific item types.
 */
public interface Item {
    /**
     * Retrieves the unique or display name of the item.
     * @return The item's name.
     */
    String getName();

    /**
     * Retrieves the descriptive text associated with the item.
     * @return A detailed description of the item.
     */
    String getDescription();
}