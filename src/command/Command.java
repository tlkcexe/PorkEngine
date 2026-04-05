package command;

import engine.GameState;
import java.util.List;

public interface Command {
    void execute(List<String> args, GameState gameState);
}