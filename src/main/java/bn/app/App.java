package bn.app;

import bn.gameInstance.GameController;
import bn.gameInstance.GameInstance;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  public static boolean sameComputerDevMode = true;

  public static GameInstance gameInstance = null;
  public static int selfPort = -1;
  
  public static String otherAddress = "localhost";
  public static int otherPort = -1;

  @Override
  public void start(Stage unusedStage) {
    try {
      String firstFxml = "/bn/fxml/StartMenu.fxml";
      WindowWrapper primaryWindowWrapper = new WindowWrapper(firstFxml, "first");
      primaryWindowWrapper.getWindowSM().show();

    } catch (Exception e) {
      System.out.println("[Error: " + e.getMessage() + "]");
    }
  }

  public static void consoleTesting() {
    System.out.println("boas");
  }

  public static int main(String[] args) {
    if (args.length > 0) {
      otherAddress = args[0];
    }

    if (args.length > 1) {
      try {
        otherPort = Integer.parseInt(args[1]);
      } catch (Exception e) {
        System.err.println("Invalid other port number: " + args[0]);

        return -1;
      }
    }
    
    if (args.length > 2) {
      try {
        selfPort = Integer.parseInt(args[2]);
      } catch (Exception e) {
        System.err.println("Invalid self port number: " + args[0]);

        return -1;
      }
    }

    consoleTesting();
    launch();

    return 0;
  }

}
