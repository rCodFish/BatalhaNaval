package bn.gameInstance;

import bn.data.grid.Grid;

/**
 *
 * @author Eduardo Santos
 */
public class UXController {
  private boolean isPrep;
  private boolean isPrepFinished;
  
  private Grid playerGrid;
  
  public UXController(boolean isHost){
    playerGrid = new Grid(10, 10);
  }
  
  public void myPlay(){
  }
  
  public void otherPlay(){    
  }
  
  public boolean otherPlayHit(int x, int y){
    return true;
  }
  
  public void OtherEnd(){
  }
  
}
