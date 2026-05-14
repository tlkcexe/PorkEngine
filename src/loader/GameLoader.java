package loader;

import model.Room;

/**
 * The GameLoader interface establishes the contract for loading game content.
 * By abstracting the loading mechanism (Strategy Pattern), the engine can support 
 * multiple formats (JSON, YAML, XML) without modifying core execution logic.
 */
public interface GameLoader {
    /**
     * Parses the external data source and populates the domain model.
     * @param filePath The path to the external configuration file.
     * @return The designated starting Room of the loaded game instance.
     */
    Room loadGame(String filePath);
}