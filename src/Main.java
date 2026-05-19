import engine.GameEngine;
import engine.GameState;
import ui.ConsoleUI;
import loader.JsonGameLoader;
import loader.RoomRegistry;
import model.PlayerImpl;
import model.Room;
import command.*;

/**
 * The Main class serves as the composition root for the text-based adventure engine.
 * It is responsible for initializing the core subsystems, injecting dependencies,
 * and wiring the command registry. By keeping instantiation here, the engine remains
 * decoupled from specific game content and command implementations.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();

        // 1. Data-Driven Game Loading Phase
        RoomRegistry registry = new RoomRegistry();
        JsonGameLoader loader = new JsonGameLoader(registry);
        Room startingRoom = loader.loadGame("resources/demo_game.json");

        if (startingRoom == null) {
            ui.printError("Critical Error: Failed to load the external game definitions. Exiting...");
            return;
        }

        // 2. State Initialization
        PlayerImpl player = new PlayerImpl();
        player.setCurrentRoom(startingRoom);
        GameState gameState = new GameState(player, registry);

        // 3. Command System Wiring (Open/Closed Principle application)
        SynonymMap synMap = new SynonymMap("resources/synonyms.txt");
        CommandParser parser = new CommandParser(synMap);
        CommandRegistry cmdRegistry = new CommandRegistry();

        // Registering available core commands dynamically
        cmdRegistry.register("go", new GoCommand());
        cmdRegistry.register("look", new LookCommand());
        cmdRegistry.register("take", new TakeCommand());
        cmdRegistry.register("drop", new DropCommand());
        cmdRegistry.register("inventory", new InventoryCommand());
        cmdRegistry.register("use", new UseCommand());
        cmdRegistry.register("talk", new TalkCommand());
        cmdRegistry.register("give", new GiveCommand());
        
        // Registering newly added feature commands
        cmdRegistry.register("quit", new QuitCommand());
        cmdRegistry.register("exit", new QuitCommand());
        cmdRegistry.register("history", new HistoryCommand());
        cmdRegistry.register("difficulty", new DifficultyCommand());
        cmdRegistry.register("state", new StateCommand());
        cmdRegistry.register("save", new SaveCommand());
        cmdRegistry.register("load", new LoadCommand());

        CommandDispatcher dispatcher = new CommandDispatcherImpl(parser, cmdRegistry);

        // 4. Engine Initialization & Execution
        GameEngine engine = new GameEngine(gameState, ui, dispatcher);
        engine.start();
    }
}