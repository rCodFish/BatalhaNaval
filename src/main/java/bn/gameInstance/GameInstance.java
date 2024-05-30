package bn.gameInstance;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {

  private static GameInstance gameInstance;
  private GameController connection;
  private UXController uXController;

  private final boolean isMultiplayer;
  private Thread connectionThread;

  public GameInstance(boolean isMultiplayer) throws Exception {
    this.uXController = new UXController();
    this.isMultiplayer = isMultiplayer;
    setupGame();
  }

  public static void setGameInstance(GameInstance instance) {
    gameInstance = instance;
  }

  public static GameInstance getGameInstance() {
    return gameInstance;
  }

  private void setupGame() {
    if (isMultiplayer) {
      multiplayerSetup();
    } else {
      singleplayerSetup();
    }
  }

  private void multiplayerSetup() {
    connection = new GameController(uXController);
    connectionThread = new Thread(connection);
    connectionThread.start();
  }

  public GameController getCon() {
    return connection;
  }

  private void singleplayerSetup() {
    //to do
  }

}
