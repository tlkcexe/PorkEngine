package engine;

import ui.ConsoleUI;

public class GameEngine {
    private GameState gameState;
    private ConsoleUI ui;

    public GameEngine(GameState gameState, ConsoleUI ui) {
        this.gameState = gameState;
        this.ui = ui;
    }

    // Η μέθοδος που ξεκινάει το παιχνίδι
    public void start() {
        ui.printMessage("Welcome to the Text Adventure Engine!");
        
        // Το κεντρικό Core Loop (Game Loop)
        while (!gameState.isGameOver()) {
            // 1. Ζητάμε είσοδο από τον χρήστη
            String input = ui.getUserInput();
            
            // 2. Ελέγχουμε αν θέλει να βγει
            if (input.equals("quit") || input.equals("exit")) {
                gameState.setGameOver(true);
                ui.printMessage("Goodbye!");
                continue;
            }
            
            // 3. (ΠΡΟΣΩΡΙΝΟ) Απλώς τυπώνουμε αυτό που έγραψε.
            // Αργότερα, εδώ θα καλούμε τον CommandDispatcher του Σωτήρη!
            ui.printMessage("System echo: You typed '" + input + "'");
        }
    }
}