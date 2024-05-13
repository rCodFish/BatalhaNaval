package bn.gameInstance;

import bn.gameModes.Multiplayer.PVPDN;
import bn.gameModes.Multiplayer.PVPSC;
import bn.gameModes.Multiplayer.PVPSN;
import bn.gameModes.Multiplayer.sameNetworkConnection.Connection;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Santos
 */
public class GameInstance {
  
  private static final ArrayList<String> availableGameModes = new ArrayList<>();
  static{
    availableGameModes.add("PVPSN");
    availableGameModes.add("PVPDN");
    availableGameModes.add("PVPSC");
    availableGameModes.add("PVE");
  }
  
  private final String gameMode;
  private boolean isHost;

  public GameInstance(String gm, boolean isHost) throws Exception {
    this.gameMode = validateGameMode(gm);
    setupGame(isHost);
  }

  private String validateGameMode(String gm) throws Exception {
    if (availableGameModes.contains(gm)) {
      return gm;
    } else {
      throw new Exception("[Error: Invalid gamemode]");
    }
  }

  private void setupGame(boolean isHost) {
    if (isMultiplayer()) {
      this.isHost = isHost;
      multiplayerSetup();
    } else {
      this.isHost = true;
      singleplayerSetup();
    }
  }

  private boolean isMultiplayer() {
    return gameMode.startsWith("PVP");
  }

  private void multiplayerSetup() {
    switch(gameMode){
      case "PVPSN":
        Connection connection = new Connection(isHost); 
        connection.startConnecting(); // if host opens to a port and waits for another game instance not host to connect to it and vice versa
        PVPSN pvpsn = new PVPSN();
        break;
      case "PVPDN":
        PVPDN pvpdn = new PVPDN();
        break;
      case "PVPSC":
        PVPSC pvpsc = new PVPSC();
        break;
    }
  }

  private void singleplayerSetup() {
    
  }

}
