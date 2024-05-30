package bn.gameInstance;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {
  private static GameInstance gameInstance;
  private final boolean isMultiplayer;
  private UXController uXController;
  private Thread connectionThread;
  private GameController connection;

  public GameInstance(boolean isMultiplayer) throws Exception {
    this.uXController = new UXController();
    this.isMultiplayer = isMultiplayer;
    setupGame();
  }

  public static void setGameInstance(GameInstance gameInstance) {
    gameInstance = gameInstance;
  }
  
  public static void getGameInstance(GameInstance gameInstance) {
    gameInstance = gameInstance;
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
    connection = new GameController(uXController);
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
