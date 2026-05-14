package command;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains the mappings between canonical action verbs and their corresponding Command implementations.
 * This class eliminates the need for hardcoded if/switch statements, allowing the system 
 * to resolve behaviors dynamically in O(1) time complexity.
 */
public class CommandRegistry {
    private final Map<String, Command> commands;

    public CommandRegistry() {
        this.commands = new HashMap<>();
    }

    /**
     * Registers a new command behavior into the engine.
     * @param verb The canonical verb (e.g., "go", "take").
     * @param command The implementation class mapped to the verb.
     */
    public void register(String verb, Command command) {
        commands.put(verb.toLowerCase(), command);
    }

    /**
     * Resolves the appropriate command instance based on the input verb.
     * @param verb The canonical verb to look up.
     * @return The registered Command, or null if the verb is unrecognized.
     */
    public Command getCommand(String verb) {
        return commands.get(verb.toLowerCase());
    }
}