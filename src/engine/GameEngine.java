package engine;

import ui.ConsoleUI;
import command.CommandDispatcher;

/**
 * The GameEngine class operates the central game loop.
 * It acts as the primary controller, coordinating user input parsing,
 * and command delegation via the dispatcher.
 */
public class GameEngine {
    private GameState gameState;
    private ConsoleUI ui;
    private CommandDispatcher dispatcher;

    public GameEngine(GameState gameState, ConsoleUI ui, CommandDispatcher dispatcher) {
        this.gameState = gameState;
        this.ui = ui;
        this.dispatcher = dispatcher;
    }

    /**
     * Initializes the user interface and starts the main execution loop.
     * The loop continuously listens for user input until a termination flag is triggered.
     */
    public void start() {
        ui.printMessage("\n=======================================================");
        ui.printMessage("                   P O R K E N G I N E                 ");
        ui.printMessage("                    The Escape v0.3                    ");
        ui.printMessage("=======================================================");
        
        // Render the initial context for the player
        ui.printRoomHeader(gameState.getPlayer().getCurrentRoom().getName());
        ui.printMessage(gameState.getPlayer().getCurrentRoom().getDescription());

        // The core game loop
        while (!gameState.isGameOver()) {
            String input = ui.getUserInput();
            
            // Hardcoded fallback for immediate termination
            if (input.equals("quit") || input.equals("exit")) {
                gameState.setGameOver(true);
                ui.printMessage("Goodbye! Thanks for playing.");
                continue;
            }
            
            // Delegate user input to the robust Command subsystem
            dispatcher.dispatch(input, gameState, ui);
        }
    }
}