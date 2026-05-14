package model;

/**
 * Defines the contract for Non-Player Characters (NPCs) within the game world.
 * NPCs operate as autonomous entities driven by a Finite State Machine (FSM).
 * Their behavior and dialog shift dynamically based on their current state,
 * which is influenced by player interactions.
 */
public interface Npc {
    String getId();
    String getName();
    String getDescription();
    
    // --- State Machine Capabilities ---
    String getCurrentState();
    void setCurrentState(String state);
    
    // --- Interaction Mechanisms ---
    /** @return The dialog string corresponding to the NPC's current state. */
    String talk();
    void addDialog(String state, String text);
    
    /** @return The item ID required to trigger a state transition (e.g., unlocking a path). */
    String getRequiredItemId();
}