package engine;

import model.Player;

public class GameState {
    private Player player;
    private boolean gameOver;

    public GameState(Player player) {
        this.player = player;
        this.gameOver = false; // Το παιχνίδι ξεκινάει πάντα ως "ενεργό"
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}