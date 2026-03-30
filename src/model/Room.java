package model;

import java.util.List;

public interface Room {
    String getId();          // Ένα μοναδικό ID για το JSON (π.χ. "room_start")
    String getName();        // Το όνομα του δωματίου (π.χ. "Dark Cave")
    String getDescription(); // Η περιγραφή που βλέπει ο παίκτης

    // Διαχείριση αντικειμένων στο δωμάτιο
    List<Item> getItems();
    void addItem(Item item);
    void removeItem(Item item);
    Item getItemByName(String itemName);

    // Διαχείριση εξόδων (προς τα πού μπορεί να πάει ο παίκτης)
    List<Exit> getExits();
    void addExit(Exit exit);
    Exit getExitByDirection(String direction);
}