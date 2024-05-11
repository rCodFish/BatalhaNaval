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

  boolean host = false;
  boolean client = !host;

  public PVPSN(boolean host) {
    
    if (host) {
      this.host = true;
    }
    
  }

  public void startGame() throws Exception {
    boolean finished = false;
    
    //main game loop
    while (!finished) {
      Grid player = new Grid(10, 10);
      Grid enemyPlayer = new Grid(10, 10);
      
      setupPhase();
      
      
    }
  }
  
  private void setupPhase() throws Exception {
    
  }
  
}
