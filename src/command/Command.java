package command;

import engine.GameState;
import ui.ConsoleUI;
import java.util.List;

public interface Command {
    void execute(List<String> args, GameState gameState, ConsoleUI ui);
}