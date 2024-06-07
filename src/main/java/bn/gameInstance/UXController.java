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
  
  abstract public void setMySceneTransitionRdy();
  
  abstract public boolean isMySceneTransitionRdy();
  
  abstract public boolean isOtherSceneTransitionRdy();
  
  abstract public void start() throws Exception;

  abstract public void stop() throws Exception;

  abstract public void otherRdyToTransition();
  
  abstract public void otherPlaying();
    
  abstract public void imPlaying();

  abstract public boolean otherHit(int x, int y);

  abstract public void setBoats(ArrayList<Boat> placedBoats);
  
  abstract public ArrayList<Boat> getBoats();
  
  abstract public boolean amIFirstPlaying();
}
