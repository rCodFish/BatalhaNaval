
package bn.gameInstance;

import bn.data.boat.Boat;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Santos
 */
abstract public class LogicController {
  GameEngine gameInstance;
  
  public LogicController(GameEngine gameInstance) {
    this.gameInstance = gameInstance;
  }
  
  abstract public void start() throws Exception;

  abstract public void stop() throws Exception;
 
  abstract public void myPlayFinished();

  abstract public void myPlayStarted();

  abstract public void myGameFinished();
  
  abstract public void myPrepFinished();

  abstract public boolean hitOther(int x, int y);
  
  abstract public void otherPartyFinishedPrep();

  abstract public void imPlaying();

  abstract public void otherPlaying();

  abstract public boolean otherPlayHit(int x, int y);

  abstract public void otherPartyFinishedGame();

  abstract public void otherPartyIsReadyToPlay();
}
