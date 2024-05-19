package bn.gameInstance;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {

  private boolean isHost;
  private final boolean isMultiplayer;
  private UXController stateMachine;
  private Thread connectionThread;
  private GameController connection;

  public GameInstance(boolean isMultiplayer, boolean isHost) throws Exception {
    this.isHost = isHost;
    this.isMultiplayer = isMultiplayer;
    setupGame();
  }

  private void setupGame() {
    if (isMultiplayer) {
      multiplayerSetup();
    } else {
      this.isHost = true;
      singleplayerSetup();
    }
  }

  private void multiplayerSetup() {
    connection = new GameController(stateMachine);
    connectionThread = new Thread(connection);
    connectionThread.start();
    
  }

  private void singleplayerSetup() {
    //to do
  }

}
