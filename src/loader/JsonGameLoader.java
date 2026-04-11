package loader;

import model.Exit;
import model.Room;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Loader που διαβάζει ένα JSON αρχείο και φτιάχνει Room objects με Exit objects.
public class JsonGameLoader implements GameLoader {

    @Override
    public Room loadGame(String filePath) {
        // Διαβάζω το αρχείο σε ένα String.
        String fileText = readFile(filePath);
        if (fileText == null) {
            return null;
        }

        // Αφαιρώ νέα γραμμή για πιο απλό parsing.
        String json = fileText.replace('\r', ' ').replace('\n', ' ').trim();
        RoomRegistry registry = new RoomRegistry();
        List<RoomDef> rooms = parseRooms(json);

        // 1ο πέρασμα: Δημιουργώ όλα τα δωμάτια και τα βάζω στον registry.
        for (RoomDef roomDef : rooms) {
            if (roomDef.id == null) {
                continue;
            }
            String name = roomDef.name != null ? roomDef.name : roomDef.id;
            String description = roomDef.description != null ? roomDef.description : "";
            JsonRoom room = new JsonRoom(roomDef.id, name, description);
            registry.addRoom(roomDef.id, room);
        }

        // 2ο πέρασμα: Συνδέω τις εξόδους με τα room ids που υπάρχουν στον registry.
        for (RoomDef roomDef : rooms) {
            Room sourceRoom = registry.getRoom(roomDef.id);
            if (sourceRoom == null) {
                continue;
            }
            List<ExitDef> exits = parseExits(roomDef.exitsText);
            for (ExitDef exitDef : exits) {
                if (exitDef.targetRoomId == null) {
                    continue;
                }
                if (!registry.hasRoom(exitDef.targetRoomId)) {
                    continue;
                }
                sourceRoom.addExit(new JsonExit(exitDef.direction, exitDef.targetRoomId, exitDef.locked, exitDef.requiredItemId));
            }
        }

        // Παίρνω το startRoom είτε από "startRoom" είτε από "startingRoom".
        String startRoomId = findStringValue(json, "startRoom");
        if (startRoomId == null) {
            startRoomId = findStringValue(json, "startingRoom");
        }
        if (startRoomId == null) {
            return null;
        }

        return registry.getRoom(startRoomId);
    }

    // Διαβάζει όλο το αρχείο JSON από το disk.
    private String readFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("JsonGameLoader: cannot read file " + filePath);
            return null;
        }
    }

    // Διαβάζει τη λίστα από το πεδίο rooms του JSON.
    private List<RoomDef> parseRooms(String json) {
        List<RoomDef> result = new ArrayList<>();
        String roomsArray = findValueText(json, "rooms");
        if (roomsArray == null || !roomsArray.startsWith("[")) {
            return result;
        }

        String inner = roomsArray.substring(1, roomsArray.length() - 1).trim();
        List<String> roomObjects = splitTopLevelEntries(inner);
        for (String roomJson : roomObjects) {
            String id = findStringValue(roomJson, "id");
            String name = findStringValue(roomJson, "name");
            String description = findStringValue(roomJson, "description");
            String exitsText = findValueText(roomJson, "exits");
            result.add(new RoomDef(id, name, description, exitsText));
        }
        return result;
    }

    // Διαβάζει τα exits είτε σαν map είτε σαν array αντικειμένων.
    private List<ExitDef> parseExits(String exitsText) {
        List<ExitDef> result = new ArrayList<>();
        if (exitsText == null) {
            return result;
        }

        String trimmed = exitsText.trim();
        if (trimmed.startsWith("{")) {
            // Μορφή { "north": "room2" }
            String inner = trimmed.substring(1, trimmed.length() - 1).trim();
            List<String> items = splitTopLevelEntries(inner);
            for (String item : items) {
                ExitDef exit = parseExitMapEntry(item);
                if (exit != null) {
                    result.add(exit);
                }
            }
        } else if (trimmed.startsWith("[")) {
            // Μορφή [ {"direction": ..., "targetRoom": ... } ]
            String inner = trimmed.substring(1, trimmed.length() - 1).trim();
            List<String> items = splitTopLevelEntries(inner);
            for (String item : items) {
                if (item.startsWith("{")) {
                    String direction = findStringValue(item, "direction");
                    String targetRoom = findStringValue(item, "targetRoom");
                    if (targetRoom == null) {
                        targetRoom = findStringValue(item, "targetRoomId");
                    }
                    boolean locked = findBooleanValue(item, "isLocked");
                    String requiredItem = findStringValue(item, "requiredItemId");
                    result.add(new ExitDef(direction, targetRoom, locked, requiredItem));
                }
            }
        }

        return result;
    }

    // Διαβάζει είσοδο του τύπου "direction": "roomId".
    private ExitDef parseExitMapEntry(String entry) {
        int firstQuote = entry.indexOf('"');
        if (firstQuote < 0) {
            return null;
        }
        int secondQuote = entry.indexOf('"', firstQuote + 1);
        if (secondQuote < 0) {
            return null;
        }
        String direction = entry.substring(firstQuote + 1, secondQuote);
        int colon = entry.indexOf(':', secondQuote);
        if (colon < 0) {
            return null;
        }
        int quoteStart = entry.indexOf('"', colon);
        if (quoteStart < 0) {
            return new ExitDef(direction, null, false, null);
        }
        int quoteEnd = entry.indexOf('"', quoteStart + 1);
        if (quoteEnd < 0) {
            return new ExitDef(direction, null, false, null);
        }
        String targetRoom = entry.substring(quoteStart + 1, quoteEnd);
        return new ExitDef(direction, targetRoom, false, null);
    }

    // Διαβάζει ένα string value από το JSON.
    private String findStringValue(String text, String key) {
        String raw = findValueText(text, key);
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            return trimmed.substring(1, trimmed.length() - 1);
        }
        return null;
    }

    private boolean findBooleanValue(String text, String key) {
        String raw = findValueText(text, key);
        if (raw == null) {
            return false;
        }
        String trimmed = raw.trim().toLowerCase();
        return trimmed.equals("true");
    }

    // Βρίσκει την τιμή μετά από ένα key, χωρίς να ελέγχει πέρα από τα βασικά.
    private String findValueText(String text, String key) {
        String token = '"' + key + '"';
        int idx = text.indexOf(token);
        if (idx < 0) {
            return null;
        }
        int colon = text.indexOf(':', idx + token.length());
        if (colon < 0) {
            return null;
        }
        int i = colon + 1;
        while (i < text.length() && Character.isWhitespace(text.charAt(i))) {
            i++;
        }
        if (i >= text.length()) {
            return null;
        }
        char c = text.charAt(i);
        if (c == '"') {
            int end = i + 1;
            while (true) {
                end = text.indexOf('"', end);
                if (end < 0) {
                    return null;
                }
                if (text.charAt(end - 1) != '\\') {
                    break;
                }
                end++;
            }
            return text.substring(i, end + 1);
        }
        if (c == '{' || c == '[') {
            int end = findMatchingBracket(text, i);
            if (end < 0) {
                return null;
            }
            return text.substring(i, end + 1);
        }
        int end = i;
        while (end < text.length()) {
            char ch = text.charAt(end);
            if (ch == ',' || ch == '}' || ch == ']') {
                break;
            }
            end++;
        }
        return text.substring(i, end).trim();
    }

    // Βοηθητική μέθοδος για να βρω το κλείσιμο ενός { } ή [ ].
    private int findMatchingBracket(String text, int start) {
        char openChar = text.charAt(start);
        char closeChar = openChar == '{' ? '}' : openChar == '[' ? ']' : 0;
        if (closeChar == 0) {
            return -1;
        }
        int depth = 0;
        boolean inQuotes = false;
        for (int i = start; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '"' && (i == 0 || text.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }
            if (inQuotes) {
                continue;
            }
            if (c == openChar) {
                depth++;
            } else if (c == closeChar) {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Σπάει στοιχεία στο υψηλότερο επίπεδο, χωρίς να σπάσει εσωτερικά αντικείμενα.
    private List<String> splitTopLevelEntries(String text) {
        List<String> items = new ArrayList<>();
        int depth = 0;
        boolean inQuotes = false;
        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '"' && (i == 0 || text.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }
            if (inQuotes) {
                continue;
            }
            if (c == '{' || c == '[') {
                depth++;
            } else if (c == '}' || c == ']') {
                depth--;
            } else if (c == ',' && depth == 0) {
                items.add(text.substring(start, i).trim());
                start = i + 1;
            }
        }
        if (start < text.length()) {
            String last = text.substring(start).trim();
            if (!last.isEmpty()) {
                items.add(last);
            }
        }
        return items;
    }

    private static class RoomDef {
        String id;
        String name;
        String description;
        String exitsText;

        RoomDef(String id, String name, String description, String exitsText) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.exitsText = exitsText;
        }
    }

    private static class ExitDef {
        String direction;
        String targetRoomId;
        boolean locked;
        String requiredItemId;

        ExitDef(String direction, String targetRoomId, boolean locked, String requiredItemId) {
            this.direction = direction;
            this.targetRoomId = targetRoomId;
            this.locked = locked;
            this.requiredItemId = requiredItemId;
        }
    }

    private static class JsonRoom implements Room {
        private String id;
        private String name;
        private String description;
        private List<Exit> exits = new ArrayList<>();
        private List<model.Item> items = new ArrayList<>();

        JsonRoom(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public List<model.Item> getItems() {
            return items;
        }

        @Override
        public void addItem(model.Item item) {
            if (item != null) {
                items.add(item);
            }
        }

        @Override
        public void removeItem(model.Item item) {
            items.remove(item);
        }

        @Override
        public model.Item getItemByName(String itemName) {
            if (itemName == null) {
                return null;
            }
            for (model.Item item : items) {
                if (itemName.equalsIgnoreCase(item.getName())) {
                    return item;
                }
            }
            return null;
        }

        @Override
        public List<Exit> getExits() {
            return exits;
        }

        @Override
        public void addExit(Exit exit) {
            if (exit != null) {
                exits.add(exit);
            }
        }

        @Override
        public Exit getExitByDirection(String direction) {
            if (direction == null) {
                return null;
            }
            for (Exit exit : exits) {
                if (direction.equalsIgnoreCase(exit.getDirection())) {
                    return exit;
                }
            }
            return null;
        }
    }

    private static class JsonExit implements Exit {
        private String direction;
        private String targetRoomId;
        private boolean locked;
        private String requiredItemId;

        JsonExit(String direction, String targetRoomId, boolean locked, String requiredItemId) {
            this.direction = direction;
            this.targetRoomId = targetRoomId;
            this.locked = locked;
            this.requiredItemId = requiredItemId;
        }

        @Override
        public String getDirection() {
            return direction;
        }

        @Override
        public String getTargetRoomId() {
            return targetRoomId;
        }

        @Override
        public boolean isLocked() {
            return locked;
        }

        @Override
        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        @Override
        public String getRequiredItemId() {
            return requiredItemId;
        }
    }
}
