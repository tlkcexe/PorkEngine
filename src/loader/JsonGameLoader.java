package loader;

import com.google.gson.Gson;
import model.*;

import java.io.FileReader;
import java.io.Reader;

/**
 * The JsonGameLoader is responsible for deserializing the external game state from a JSON file.
 * It enforces the data-driven architecture requirement by ensuring that no game content 
 * is hardcoded into the Java source files.
 */
public class JsonGameLoader implements GameLoader {
    private RoomRegistry roomRegistry;
    private String startingRoomId;

    public JsonGameLoader(RoomRegistry registry) {
        this.roomRegistry = registry;
    }

    @Override
    public Room loadGame(String filePath) {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            // 1. Deserialize the entire JSON structure into Data Transfer Objects (DTOs)
            GameDataDTO gameData = gson.fromJson(reader, GameDataDTO.class);
            this.startingRoomId = gameData.startingRoom;

            // 2. Map DTOs to the actual Domain Model implementations
            for (GameDataDTO.RoomDTO rDTO : gameData.rooms) {
                RoomImpl room = new RoomImpl(rDTO.id, rDTO.name, rDTO.description);

                // Populate interactable items
                if (rDTO.items != null) {
                    for (GameDataDTO.ItemDTO iDTO : rDTO.items) {
                        room.addItem(new ItemImpl(iDTO.name, iDTO.description));
                    }
                }

                // Populate navigation exits
                if (rDTO.exits != null) {
                    for (GameDataDTO.ExitDTO eDTO : rDTO.exits) {
                        room.addExit(new ExitImpl(eDTO.direction, eDTO.targetRoom, eDTO.isLocked, eDTO.requiredItem));
                    }
                }

                // Register the fully constructed room into the in-memory database
                roomRegistry.addRoom(room);
            }

            System.out.println("[SYSTEM] Game loaded successfully: " + gameData.gameName);
            return roomRegistry.getRoom(startingRoomId);

        } catch (Exception e) {
            System.err.println("[CRITICAL ERROR] Failed to parse or load the JSON game file at: " + filePath);
            System.err.println("Ensure the file exists and is strictly formatted as valid JSON.");
            e.printStackTrace();
            return null;
        }
    }
}