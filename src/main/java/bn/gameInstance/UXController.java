package bn.gameInstance;


import bn.data.grid.Grid;
//import bn.gui.supportingLogic.windows.WinStateMachine;

/**
 *
 * @author Eduardo Santos
 */
public class UXController {
  private boolean isPrep;
  private boolean isPrepFinished;

  //private WinStateMachine gameWinSM = gameWinWrapper.getWindowAPI();
  
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
