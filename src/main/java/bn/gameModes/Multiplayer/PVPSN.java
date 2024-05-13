package bn.gameModes.Multiplayer;

import bn.data.grid.Grid;

/**
 * ======WIP======
 *
 * @author Eduardo Santos
 *
 * PVP game mode same network
 */
public class PVPSN implements MultiplayerGameMode {

  public PVPSN() {
  }
  
  @Override
  public void startGame(boolean isHost) throws Exception {
    
    Grid player = new Grid(10, 10);
    Grid enemyPlayer = new Grid(10, 10);
    
    if(isHost){
    }
    
    //main game loop
    while (!gameOver()) { 
      
      setupPhase();
      
      
    }
  }
  
  private void setupPhase() throws Exception {
    
  }
  
  private boolean gameOver() {
        // Determine game over conditions
        return false; // Modify as needed
    }
  
  
}
