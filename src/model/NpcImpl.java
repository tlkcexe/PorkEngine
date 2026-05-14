package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of the Npc interface.
 * Utilizes a map-based dictionary to resolve dialog responses dynamically
 * based on the character's active state.
 */
public class NpcImpl implements Npc {
    private final String id;
    private final String name;
    private final String description;
    private final String requiredItemId;
    
    // Volatile state modified via player interaction
    private String currentState;
    
    // Dictionary mapping specific states to dialog outputs
    private final Map<String, String> dialogs;

    public NpcImpl(String id, String name, String description, String startingState, String requiredItemId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentState = startingState;
        this.requiredItemId = requiredItemId;
        this.dialogs = new HashMap<>();
    }

    @Override public String getId() { return id; }
    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public String getCurrentState() { return currentState; }
    @Override public void setCurrentState(String state) { this.currentState = state; }
    @Override public String getRequiredItemId() { return requiredItemId; }

    @Override
    public void addDialog(String state, String text) {
        dialogs.put(state, text);
    }

    @Override
    public String talk() {
        // Fallback response if the current state lacks a mapped dialog entry
        return dialogs.getOrDefault(currentState, "... (stares at you in silence)");
    }
}