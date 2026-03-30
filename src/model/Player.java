package model;

import java.util.List;

public interface Player {
    // Διαχείριση τοποθεσίας
    Room getCurrentRoom();
    void setCurrentRoom(Room room);

    // Διαχείριση Inventory
    List<Item> getInventory();
    void addItem(Item item);
    void removeItem(Item item);
    boolean hasItem(String itemName);
}