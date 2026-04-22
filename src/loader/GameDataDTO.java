package loader;

import java.util.List;
import java.util.Map;

/**
 * GameDataDTO serves as a collection of Data Transfer Objects.
 * These classes strictly mirror the external JSON structure. 
 * They contain no business logic and exist solely to facilitate 
 * the safe deserialization of data via the Gson library.
 */
public class GameDataDTO {
    public String gameName;
    public String startingRoom;
    public List<RoomDTO> rooms;

    public static class RoomDTO {
        public String id;
        public String name;
        public String description;
        public List<ItemDTO> items;
        public List<ExitDTO> exits;
        public List<NpcDTO> npcs; 
    }

    public static class ItemDTO {
        public String id;
        public String name;
        public String description;
    }

    public static class ExitDTO {
        public String direction;
        public String targetRoom;
        public boolean isLocked;
        public String requiredItem;
    }

    public static class NpcDTO {
        public String id;
        public String name;
        public String description;
        public String startingState;
        public String requiredItem;
        public Map<String, String> dialogs;
    }
}