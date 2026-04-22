package engine;

import model.Player;
import model.Room;
import loader.RoomRegistry;

/**
 * Represents the current session's state within the game engine.
 * Tracks the player's status and provides access to the loaded game world.
 * (v0.3: Pre-Event System state)
 */
public class GameState {
    private Player player;
    private boolean gameOver;
    private RoomRegistry roomRegistry;

    public GameState(Player player, RoomRegistry roomRegistry) {
        this.player = player;
        this.roomRegistry = roomRegistry;
        this.gameOver = false;
    }

    public Player getPlayer() { return player; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    
    public Room getRoomById(String id) { 
        return roomRegistry.getRoom(id); 
    }
}