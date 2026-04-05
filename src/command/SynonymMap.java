package command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SynonymMap {
    private Map<String, String> synonymMap;

    public SynonymMap(String filePath) {
        synonymMap = new HashMap<>();
        loadFromFile(filePath);
    }

    private void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Αγνοούμε κενές γραμμές ή σχόλια
                if (line.isEmpty() || line.startsWith("#")) continue;

                // Χωρίζουμε την κύρια εντολή από τα συνώνυμα βάσει της άνω-κάτω τελείας
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String primaryVerb = parts[0].trim().toLowerCase();
                    synonymMap.put(primaryVerb, primaryVerb); // Βάζουμε και την ίδια τη λέξη

                    // Διαβάζουμε τα συνώνυμα που χωρίζονται με κόμμα
                    String[] synonyms = parts[1].split(",");
                    for (String syn : synonyms) {
                        synonymMap.put(syn.trim().toLowerCase(), primaryVerb);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[SYSTEM ERROR] Failed to load synonyms from: " + filePath);
        }
    }

    // Αν η λέξη δεν υπάρχει στα συνώνυμα, επιστρέφει την ίδια (fallback)
    public String getPrimaryVerb(String word) {
        return synonymMap.getOrDefault(word.toLowerCase(), word.toLowerCase());
    }
}