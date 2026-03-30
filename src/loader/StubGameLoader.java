package loader;

import model.Room;

public class StubGameLoader implements GameLoader {
    
    @Override
    public Room loadGame(String filePath) {
        // ΠΡΟΣΟΧΗ: Αυτό είναι αυστηρά προσωρινό (Stub) για τις Εβδομάδες 1-2!
        // Στην v0.2 (Εβδομάδες 3-4) αυτό θα διαγραφεί και θα αντικατασταθεί 
        // από τον JsonGameLoader του Αλέξανδρου.
        System.out.println("[STUB] Δήθεν φορτώνω το αρχείο: " + filePath);
        System.out.println("[STUB] Ο κανονικός JSON Loader θα φτιαχτεί στο v0.2");
        
        return null; // Προς το παρόν επιστρέφουμε null, δεν φτιάχνουμε hardcoded δωμάτια για να μην χάσουμε βαθμούς.
    }
}