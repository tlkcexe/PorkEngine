package loader;

import model.Room;
import java.util.HashMap;
import java.util.Map;

/**
 * The RoomRegistry acts as an in-memory database for the game's locations.
 * It allows O(1) time complexity retrievals of Room instances by their unique identifiers,
 * ensuring efficient navigation when processing directional commands.
 */
public class RoomRegistry {
    private Map<String, Room> rooms;

    public RoomRegistry() {
        this.rooms = new HashMap<>();
    }

    /**
     * Stores a room in the registry.
     * @param room The room object to register.
     */
    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    /**
     * Retrieves a room by its unique identifier.
     * @param id The string identifier of the target room.
     * @return The Room object, or null if not found.
     */
    public Room getRoom(String id) {
        return rooms.get(id);
    }
}