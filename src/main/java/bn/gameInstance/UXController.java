package bn.gameInstance;

import bn.data.boat.Boat;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Santos
 */
abstract public class UXController {

  GameEngine gameInstance;

  public UXController(GameEngine gameInstance) {
    this.gameInstance = gameInstance;
  }

  abstract public void start() throws Exception;

  abstract public void stop() throws Exception;

  abstract public void waitingOtherPartyPrep();

  abstract public void otherPartyFinishedPrep();

  abstract public void imPlaying();

  abstract public void otherPlaying();

  abstract public boolean otherPlayHit(int x, int y);

  abstract public void otherPartyFinishedGame();

  abstract public void otherPartyIsReadyToPlay();

  abstract public void setBoats(ArrayList<Boat> placedBoats);
  
  abstract public ArrayList<Boat> getBoats();
}
