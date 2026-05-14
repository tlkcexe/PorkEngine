package command;

import engine.GameState;
import model.Npc;
import ui.ConsoleUI;

/**
 * Command implementation for retrieving dialog states from Non-Player Characters.
 * Evaluates the NPC's internal state machine to output context-appropriate text.
 */
public class TalkCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        // NLP routing: Accounts for direct ("talk guard") and prepositional ("talk to guard") phrasing
        String target = command.getFirstArg();
        if (target == null && "to".equals(command.getPreposition())) {
            target = command.getSecondArg();
        }

        if (target == null || target.isEmpty()) {
            ui.printMessage("Talk to whom? (e.g., 'talk to guard')");
            return;
        }

        // Query the spatial graph for the requested entity
        Npc npc = gameState.getPlayer().getCurrentRoom().getNpcByName(target);

        if (npc != null) {
            // Retrieve dynamic response based on current FSM state
            ui.printMessage(npc.getName() + " says: \"" + npc.talk() + "\"");
        } else {
            ui.printMessage("There is no " + target + " here to talk to.");
        }
    }
}