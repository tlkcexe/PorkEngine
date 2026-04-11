package loader;

import java.util.HashMap;
import java.util.Map;
import model.Room;

// Απλός registry για να κρατάμε δωμάτια με βάση το id τους.
public class RoomRegistry {
    private Map<String, Room> rooms = new HashMap<>();

    // Προσθέτω ένα δωμάτιο στον registry.
    public void addRoom(String id, Room room) {
        if (id == null || room == null) {
            return;
        }
        rooms.put(id, room);
    }

    // Παίρνω το δωμάτιο με βάση το id.
    public Room getRoom(String id) {
        return rooms.get(id);
    }

    // Ελέγχω αν υπάρχει το id στον registry.
    public boolean hasRoom(String id) {
        return rooms.containsKey(id);
    }
}
