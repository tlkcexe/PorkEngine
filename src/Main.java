import engine.GameEngine;
import engine.GameState;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // 1. Δημιουργούμε την "οθόνη" (UI)
        ConsoleUI ui = new ConsoleUI();

        // 2. Δημιουργούμε την κατάσταση του παιχνιδιού
        // (Προς το παρόν βάζουμε null στη θέση του παίκτη γιατί έχουμε μόνο το Interface, όχι την υλοποίηση)
        GameState gameState = new GameState(null);

        // 3. Δημιουργούμε τη μηχανή και της δίνουμε το UI και το GameState
        GameEngine engine = new GameEngine(gameState, ui);

        // 4. Πατάμε το Start!
        engine.start();
    }
}