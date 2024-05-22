package bn.app;

import bn.gameInstance.GameInstance;
import bn.gui.supportingLogic.windows.WindowWrapper;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  @Override
  public void start(Stage unusedStage) {
    try {
      
      GameInstance gameInstance = new GameInstance(true);
      
      
      /*String firstFxml = "/bn/fxml/StartMenu.fxml";
      WindowWrapper primaryWindowWrapper = new WindowWrapper(firstFxml, "first");
      primaryWindowWrapper.getWindowSM().show();*/

    } catch (Exception e) {
      System.out.println("[Error: " + e.getMessage() + "]");
    }
  }

  public static void consoleTesting() {
    System.out.println("boas");
  }

  public static void main(String[] args) {
    consoleTesting();
    launch();
  }

}
