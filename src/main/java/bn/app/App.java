package bn.app;

import bn.ui.supportingLogic.windows.WindowWrapper;
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

      String firstFxml = "/bn/fxml/primary.fxml";
      WindowWrapper primaryWindowWrapper = new WindowWrapper();
      primaryWindowWrapper.createWindow(firstFxml, 800, 400, "first");
      primaryWindowWrapper.getWindowAPI().show();

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
