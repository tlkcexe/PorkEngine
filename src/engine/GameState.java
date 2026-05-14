package engine;

import model.Player;
import model.Room;
import loader.RoomRegistry;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the current session's state within the game engine.
 * It tracks the player's status, provides access to the loaded game world (RoomRegistry),
 * and manages dynamic event flags (e.g., win/loss triggers) allowing rules to be evaluated
 * without hardcoding logic into the engine core.
 */
public class GameState {
    private Player player;
    private boolean gameOver;
    private RoomRegistry roomRegistry;
    
    // A dynamic key-value map to track custom game events (e.g., "boss_defeated")
    private Map<String, Boolean> flags;

    public GameState(Player player, RoomRegistry roomRegistry) {
        this.player = player;
        this.roomRegistry = roomRegistry;
        this.gameOver = false;
        this.flags = new HashMap<>();
    }

    public Player getPlayer() { return player; }
    
    public boolean isGameOver() { return gameOver; }
    
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    
    public Room getRoomById(String id) { 
        return roomRegistry.getRoom(id); 
    }

    /**
     * Registers or updates a specific game state flag.
     * @param flagName The unique identifier for the event.
     * @param value The boolean state of the event.
     */
    public void setFlag(String flagName, boolean value) {
        flags.put(flagName.toLowerCase(), value);
    }

    /**
     * Retrieves the status of a specific game flag.
     * @param flagName The unique identifier to check.
     * @return True if the flag is active, false otherwise.
     */
    public boolean getFlag(String flagName) {
        return flags.getOrDefault(flagName.toLowerCase(), false);
    }
}