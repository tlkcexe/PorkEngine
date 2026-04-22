package model;

/**
 * Represents a navigable transition between two rooms.
 * Defines the contract for spatial connections, including conditional access mechanisms (locks).
 */
public interface Exit {
    /**
     * Gets the directional command required to trigger this exit (e.g., "north", "up").
     * @return The direction string.
     */
    String getDirection();      

    /**
     * Gets the unique identifier of the destination room.
     * @return The target room's ID.
     */
    String getTargetRoomId();   

    /**
     * Checks if the exit is currently locked and requires a specific prerequisite to traverse.
     * @return True if the exit is locked, false otherwise.
     */
    boolean isLocked();

    /**
     * Modifies the access state of the exit.
     * @param locked The new lock state.
     */
    void setLocked(boolean locked);

    /**
     * Retrieves the identifier of the item required to unlock this exit.
     * @return The required item's ID, or null if no item is required.
     */
    String getRequiredItemId(); 
}