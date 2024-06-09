
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
 
  abstract public void send_myPlayFinished();

  abstract public void send_myPlayStarted();

  abstract public void send_myGameFinished();
  
  abstract public void send_myPrepFinished();

  abstract public void send_hitOther(int x, int y);
  
  abstract public void send_hitResponse(boolean hit, boolean win);
  
  abstract public void receive_otherFinishedPrep();

  abstract public void receive_imPlaying();

  abstract public void receive_otherPlaying();

  abstract public void receive_otherHit(int x, int y);

  abstract public void receive_otherFinishedGame();
  
  abstract public void receive_hitResponse(boolean hit, boolean win);
}
