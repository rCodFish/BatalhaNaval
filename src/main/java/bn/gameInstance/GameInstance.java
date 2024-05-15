package bn.gameInstance;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {

  private boolean isHost;
  private final boolean isMultiplayer;
  private StateMachine stateMachine;
  private Thread incoming;
  private Thread outgoing;
  private Thread connection;

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
    connection = new Thread(connection);
    
  }

  private void singleplayerSetup() {
    
  }

}
