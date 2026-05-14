package command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Resolves aliased verbs to their canonical representation by loading definitions 
 * from an external configuration file. This fulfills the data-driven architecture requirement
 * by ensuring synonym relationships are never hardcoded.
 */
public class SynonymMap {
    private final Map<String, String> synonymMap;

    public SynonymMap(String filePath) {
        this.synonymMap = new HashMap<>();
        loadFromFile(filePath);
    }

    /**
     * Parses the external text file to populate the aliasing dictionary.
     * Format expectation: "canonicalVerb:alias1,alias2,alias3"
     */
    private void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Ignore empty lines and comment lines
                if (line.isEmpty() || line.startsWith("#")) continue;

                // Split the canonical verb from its comma-separated aliases
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String primaryVerb = parts[0].trim().toLowerCase();
                    // Map the canonical verb to itself for uniform lookup
                    synonymMap.put(primaryVerb, primaryVerb); 

                    String[] synonyms = parts[1].split(",");
                    for (String syn : synonyms) {
                        synonymMap.put(syn.trim().toLowerCase(), primaryVerb);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[CRITICAL ERROR] Failed to load NLP synonyms from: " + filePath);
        }
    }

    /**
     * Retrieves the canonical verb for a given input word.
     * @param word The raw verb inputted by the user.
     * @return The canonical verb if mapped, otherwise returns the original input as a fallback.
     */
    public String getPrimaryVerb(String word) {
        return synonymMap.getOrDefault(word.toLowerCase(), word.toLowerCase());
    }
}