package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private SynonymMap synonymMap;
    // Λέξεις που αγνοούμε (filler words) για να κάνουμε τη ζωή μας πιο εύκολη
    private List<String> stopWords; 

    public CommandParser(SynonymMap synonymMap) {
        this.synonymMap = synonymMap;
        // Βάζουμε το "up" για να πιάνει το "pick up" σκέτο σαν "pick" -> "take"
        this.stopWords = Arrays.asList("the", "a", "an", "up"); 
    }

    public ParsedCommand tokenize(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        // Χωρίζουμε την πρόταση σε λέξεις (tokens) αγνοώντας τα κενά
        String[] rawTokens = input.toLowerCase().trim().split("\\s+");
        List<String> cleanTokens = new ArrayList<>();

        // Καθαρίζουμε τα filler words
        for (String token : rawTokens) {
            if (!stopWords.contains(token)) {
                cleanTokens.add(token);
            }
        }

        if (cleanTokens.isEmpty()) {
            return null;
        }

        // Η πρώτη καθαρή λέξη είναι το ρήμα
        String rawVerb = cleanTokens.get(0);
        // Μετατροπή σε βασικό ρήμα (π.χ. grab -> take)
        String primaryVerb = synonymMap.getPrimaryVerb(rawVerb);

        // Όλες οι υπόλοιπες λέξεις είναι τα ορίσματα (args)
        List<String> args = cleanTokens.subList(1, cleanTokens.size());

        return new ParsedCommand(primaryVerb, args);
    }
}