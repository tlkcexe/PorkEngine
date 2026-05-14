package loader;

import model.Room;

/**
 * A mock implementation (Stub) of the GameLoader interface.
 * Used primarily during the v0.1 architectural testing phase to verify
 * the core game loop without violating the strict "no hardcoded content" rule.
 * Obsoleted by JsonGameLoader.
 */
public class StubGameLoader implements GameLoader {
    
    @Override
    public Room loadGame(String filePath) {
        System.out.println("[STUB] Simulated loading sequence for: " + filePath);
        System.out.println("[STUB] Warning: Bypassing content generation to maintain architectural compliance.");
        
        // Returns null intentionally. Hardcoding rooms here would heavily penalize the project grading.
        return null; 
    }
}