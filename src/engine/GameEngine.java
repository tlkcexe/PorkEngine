package engine;

import ui.ConsoleUI;
import command.CommandDispatcher;

/**
 * The GameEngine class operates the central game loop.
 * It acts as the primary controller, coordinating user input parsing,
 * command delegation via the dispatcher, and evaluation of end-game conditions.
 * The hardcoded quit logic has been removed to comply with the project description.
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
            
            // Delegate user input to the robust Command subsystem
            dispatcher.dispatch(input, gameState, ui);

            // Post-command execution: Evaluate dynamically registered Game Flags
            evaluateGameConditions();
        }
    }

    /**
     * Checks dynamic game flags to determine if specific win/loss criteria have been met.
     * Uses generic, engine-level messages to maintain separation from specific game content.
     */
    private void evaluateGameConditions() {
        if (gameState.getFlag("game_won")) {
            ui.printMessage("\n=======================================================");
            ui.printMessage("                   V I C T O R Y !                     ");
            ui.printMessage("=======================================================\n");
            gameState.setGameOver(true);
        } else if (gameState.getFlag("game_lost")) {
            ui.printMessage("\n=======================================================");
            ui.printMessage("                   G A M E   O V E R                   ");
            ui.printMessage("=======================================================\n");
            gameState.setGameOver(true);
        }
    }
}