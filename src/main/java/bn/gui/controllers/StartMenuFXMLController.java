package bn.gui.controllers;

import bn.app.App;
import bn.gameInstance.GameController;
import bn.gameInstance.GameInstance;
import java.net.URL;
import java.util.ResourceBundle;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.ServerSocket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eduardo Santos
 */
public class StartMenuFXMLController extends BaseController implements Initializable {

  /**
   * Initializes the controller class.
   */
  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winAPI = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  @FXML
  private ImageView iVimg;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    winAPI.teste(iVimg);
  }

  @FXML
  public void setFullScreen() {
    winAPI.setFullScreen();
  }

  @FXML
  public void beginMultiplayer() {
    String PrepPhaseFXML = "/bn/fxml/PrepPhase.fxml";

    if (App.otherPort == -1) {
      App.selfPort = GameController.DEFAULT_SERVER_PORT;
      App.otherPort = GameController.DEFAULT_SERVER_PORT;

      if (App.sameComputerDevMode) {
        // only for usage in dev mode
        System.out.println("In dev mode ...");

        try {
          System.out.println("Trying to user self port: " + App.selfPort);

          ServerSocket socket = new ServerSocket(App.selfPort);
          socket.close();

          // Dev mode when lauching app in same computer
          App.otherPort = GameController.DEFAULT_SERVER_PORT + 1;
        } catch (Exception e) {
          App.selfPort++;

          // Dev mode when lauching app in same computer
          App.otherPort = GameController.DEFAULT_SERVER_PORT;
        }
        System.out.println("I'm in dev mode. I will use self port: " + App.selfPort + " and client port " + App.otherPort);
      }
    }

    try {
      App.gameInstance = new GameInstance(App.selfPort, "localhost", App.otherPort);
    } catch (Exception e) {
      System.err.println("Failed to start server instance: " + e);
    }

    try {
      winAPI.setRoot(PrepPhaseFXML, stage);
      winAPI.setFullScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*try{
    GameInstance gI = new GameInstance(true);
    GameInstance.setGI(gI);
    Thread.sleep(10000);
    GameController gm = gI.getCon();
    gm.prepPhaseStarted();
    }catch(Exception e){
      e.printStackTrace();
    }*/
  }

  @FXML
  public void beginGame() {
    try {
      String PrepPhaseFXML = "/bn/fxml/PrepPhase.fxml";
      winAPI.setRoot(PrepPhaseFXML, stage);
      winAPI.setFullScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void options() {
    try {
      String optFxml = "/bn/fxml/Options.fxml";
      WindowWrapper optWindowWrapper = new WindowWrapper(optFxml, "options", 15, 20);
      optWindowWrapper.getWindowSM().show();
      optWindowWrapper.getWindowSM().setSmall();
      optWindowWrapper.getStage().setAlwaysOnTop(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void close() {
    winAPI.exit();
  }

  public void minimize() {
  }

}
