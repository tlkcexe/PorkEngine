package engine;

import model.Player;
import model.Room;
import loader.RoomRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the current session's state within the game engine.
 * It tracks the player's status, provides access to the loaded game world (RoomRegistry),
 * and manages dynamic event flags (e.g., win/loss triggers) allowing rules to be evaluated
 * without hardcoding logic into the engine core. Now includes history, difficulty, and goals.
 */
public class GameState {
    private Player player;
    private boolean gameOver;
    private RoomRegistry roomRegistry;
    
    // A dynamic key-value map to track custom game events (e.g., "boss_defeated")
    private Map<String, Boolean> flags;
    
    // Tracking player's command history and extra state parameters
    private List<String> commandHistory;
    private String difficultyLevel;
    private String currentGoal;

    public GameState(Player player, RoomRegistry roomRegistry) {
        this.player = player;
        this.roomRegistry = roomRegistry;
        this.gameOver = false;
        this.flags = new HashMap<>();
        
        // Initialize default state values
        this.commandHistory = new ArrayList<>();
        this.difficultyLevel = "normal";
        this.currentGoal = "Explore the area and survive.";
    }

    public Player getPlayer() { return player; }
    
    public boolean isGameOver() { return gameOver; }
    
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    
    public Room getRoomById(String id) { 
        return roomRegistry.getRoom(id); 
    }

    /**
     * Registers or updates a specific game state flag.
     */
    public void setFlag(String flagName, boolean value) {
        flags.put(flagName.toLowerCase(), value);
    }

    /**
     * Retrieves the status of a specific game flag.
     */
    public boolean getFlag(String flagName) {
        return flags.getOrDefault(flagName.toLowerCase(), false);
    }

    // --- State management methods for added features ---

    public void addCommandToHistory(String command) {
        commandHistory.add(command);
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(String currentGoal) {
        this.currentGoal = currentGoal;
    }
}