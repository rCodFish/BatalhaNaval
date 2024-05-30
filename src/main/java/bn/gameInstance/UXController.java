package bn.gameInstance;


import bn.data.grid.Grid;

/**
 *
 * @author Eduardo Santos
 */
public class UXController {
  
  private Grid playerGrid;
  
  public UXController(){
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
