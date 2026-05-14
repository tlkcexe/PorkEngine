package command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The CommandParser acts as the Lexical Analyzer for the engine.
 * It processes raw natural language input, filters out semantic noise (stop words),
 * resolves synonyms to their canonical verbs, and identifies prepositional phrases
 * to support complex, multi-target commands.
 */
public class CommandParser {
    private final SynonymMap synonymMap;
    // Immutable lists for Natural Language Processing (NLP) filtering
    private final List<String> stopWords; 
    private final List<String> prepositions;

    public CommandParser(SynonymMap synonymMap) {
        this.synonymMap = synonymMap;
        this.stopWords = Arrays.asList("the", "a", "an", "up"); 
        this.prepositions = Arrays.asList("with", "on", "at", "to");
    }

    /**
     * Tokenizes and structures the raw user input into a ParsedCommand DTO.
     * @param input The raw string inputted by the user.
     * @return A structured ParsedCommand, or null if the input is invalid/empty.
     */
    public ParsedCommand tokenize(String input) {
        if (input == null || input.trim().isEmpty()) return null;

        String[] rawTokens = input.toLowerCase().trim().split("\\s+");
        List<String> cleanTokens = new ArrayList<>();

        // Filter out syntactic noise
        for (String token : rawTokens) {
            if (!stopWords.contains(token)) cleanTokens.add(token);
        }

        if (cleanTokens.isEmpty()) return null;

        String primaryVerb = synonymMap.getPrimaryVerb(cleanTokens.get(0));
        List<String> args = new ArrayList<>();
        String preposition = null;
        List<String> secondaryArgs = new ArrayList<>();

        // Extract primary and secondary arguments based on prepositional pivots
        boolean foundPrep = false;
        for (int i = 1; i < cleanTokens.size(); i++) {
            String token = cleanTokens.get(i);
            if (prepositions.contains(token)) {
                foundPrep = true;
                preposition = token;
                continue;
            }
            if (!foundPrep) args.add(token);
            else secondaryArgs.add(token);
        }

        return new ParsedCommand(primaryVerb, args, preposition, secondaryArgs);
    }
}