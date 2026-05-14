package command;

import engine.GameState;
import model.Exit;
import model.Item;
import model.Npc;
import ui.ConsoleUI;

/**
 * Command implementation for transferring items from the Player to an NPC.
 * This acts as a trigger for NPC State Machine transitions, allowing dynamic 
 * puzzle resolution without hardcoding specific entity interactions.
 */
public class GiveCommand implements Command {
    @Override
    public void execute(ParsedCommand command, GameState gameState, ConsoleUI ui) {
        String itemToGive = command.getFirstArg();
        String targetNpc = null;

        // NLP handling: Extract target from prepositional phrase (e.g., "give coin TO guard")
        if ("to".equals(command.getPreposition())) {
            targetNpc = command.getSecondArg();
        }

        if (itemToGive == null || targetNpc == null) {
            ui.printMessage("Give what to whom? (e.g., 'give coin to guard')");
            return;
        }

        // 1. Validate player possession
        if (!gameState.getPlayer().hasItem(itemToGive)) {
            ui.printMessage("You don't have a " + itemToGive + ".");
            return;
        }

        // 2. Validate NPC presence in the current spatial node
        Npc npc = gameState.getPlayer().getCurrentRoom().getNpcByName(targetNpc);
        if (npc == null) {
            ui.printMessage("There is no " + targetNpc + " here.");
            return;
        }

        // 3. Entity Transaction Logic
        if (itemToGive.equalsIgnoreCase(npc.getRequiredItemId())) {
            // Remove item from player's inventory
            Item item = null;
            for (Item i : gameState.getPlayer().getInventory()) {
                if (i.getName().equalsIgnoreCase(itemToGive)) {
                    item = i;
                    break;
                }
            }
            if (item != null) {
                gameState.getPlayer().removeItem(item);
            }

            // Trigger NPC State Transition
            npc.setCurrentState("happy");
            ui.printMessage("You handed the " + itemToGive + " to the " + targetNpc + ".");
            ui.printMessage(npc.getName() + " says: \"" + npc.talk() + "\"");

            // DYNAMIC WORLD EVENT: Satisfying an NPC unlocks adjacent constrained paths.
            // This maintains strict decoupling by avoiding hardcoded NPC IDs or directions.
            for (Exit exit : gameState.getPlayer().getCurrentRoom().getExits()) {
                if (exit.isLocked()) {
                    exit.setLocked(false);
                    ui.printMessage("\n*Notice* The path to the " + exit.getDirection() + " is now accessible!");
                }
            }

        } else {
            ui.printMessage(npc.getName() + " doesn't want that.");
        }
    }
}