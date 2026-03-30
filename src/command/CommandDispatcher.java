package command;

import engine.GameState;
import ui.ConsoleUI;

public interface CommandDispatcher {
    // Παίρνει το κείμενο του χρήστη, το GameState (για να αλλάξει τον κόσμο) 
    // και το UI (για να τυπώσει το αποτέλεσμα).
    void dispatch(String input, GameState gameState, ConsoleUI ui);
}