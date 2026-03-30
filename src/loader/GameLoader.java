package loader;

import model.Room;

public interface GameLoader {
    // Επιστρέφει το αρχικό δωμάτιο (Starting Room) του παιχνιδιού αφού φορτώσει τα πάντα
    Room loadGame(String filePath);
}