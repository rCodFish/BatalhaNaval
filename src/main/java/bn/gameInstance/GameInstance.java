package bn.gameInstance;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {

  private static GameInstance gameInstance;
  private GameController gameController;
  private UXController uXController;

  private int selfPort = GameController.DEFAULT_SERVER_PORT;

  private String otherAddress = null;
  private int otherPort = GameController.DEFAULT_SERVER_PORT;

  private boolean isMultiplayer = false;
  private Thread connectionThread;

  public GameInstance(int selfPort, String otherAddress, int otherPort) throws Exception {
    this.uXController = new UXController();
    
    this.isMultiplayer = true;

    this.selfPort = selfPort;
    this.otherAddress = otherAddress;
    this.otherPort = otherPort;

    setupGame();
  }

  public GameController getGameController() {
    return gameController;
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
    gameController = new GameController(uXController, selfPort, otherAddress, otherPort);
    connectionThread = new Thread(gameController);
    connectionThread.start();
  }

  public GameController getCon() {
    return gameController;
  }

  private void singleplayerSetup() {
    //to do
  }

}
