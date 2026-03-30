package model;

public interface Exit {
    String getDirection();      // Η κατεύθυνση της εξόδου (π.χ. "north", "east")
    String getTargetRoomId();   // Το ID του δωματίου στο οποίο οδηγεί (π.χ. "room_hallway")

    // Μηχανισμός για κλειδωμένες πόρτες (προετοιμασία για τις Εβδομάδες 5-6)
    boolean isLocked();
    void setLocked(boolean locked);
    String getRequiredItemId(); // Το ID του αντικειμένου που χρειάζεται για να ξεκλειδώσει (π.χ. "gold_key")
}