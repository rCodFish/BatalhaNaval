package bn.gameInstance;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {

  private final boolean isMultiplayer;
  private UXController stateMachine;
  private Thread connectionThread;
  private GameController connection;

  public GameInstance(boolean isMultiplayer) throws Exception {
    this.stateMachine = new UXController();
    this.isMultiplayer = isMultiplayer;
    setupGame();
  }

  private void setupGame() {
    if (isMultiplayer) {
      multiplayerSetup();
    }
    else{
      singleplayerSetup();
    }
  }

  private void multiplayerSetup() {
    connection = new GameController(stateMachine);
    connectionThread = new Thread(connection);
    connectionThread.start();

  }
  
  public GameController getCon(){
    return connection;
  }

  private void singleplayerSetup() {
    //to do
  }

}
