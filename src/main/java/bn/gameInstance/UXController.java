package bn.gameInstance;


import bn.data.Grid.GridCell;

/**
 *
 * @author Eduardo Santos
 */
public class UXController {
  
  private static final String GAME_GUI_CONTROLLER = "";
  
  private GridCell[][] gridBoxes;
  
  public UXController(){
    gridBoxes = new GridCell[8][8];
    populateGrid();
  }
  
  private void populateGrid(){
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        GridCell gridCell = new GridCell(x, y);
        gridBoxes[x][y] = gridCell;
      }
    }
  }
  
  public void myPlay(){
    // GAME_GUI_CONTROLLER.playerPlay();
  }
  
  public void otherPlay(){ 
    //enemyPlay();
  }
  
  public boolean otherPlayHit(int x, int y){
    return true;
  }
  
  public void otherPartyFinishedGame() {
    
  }
  
  
  public void otherPartyIsReadyToPlay(){
    
  }
  
}
