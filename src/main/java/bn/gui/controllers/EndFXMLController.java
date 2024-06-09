package bn.gui.controllers;

import bn.app.App;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos
 */
public class EndFXMLController extends GuiBaseController implements Initializable {

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  @FXML
  private Label endLabel;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    boolean didIWin = App.gameInstance.getUXController().didIWin();
    String endString = (didIWin == true) ? "You won!" : "You lost!";
    endLabel.setText(endString);
  }

  @Override
  @FXML
  public void transition() {
    String firstFxml = "/bn/fxml/StartMenu.fxml";
    try {
      winSM.setRoot(firstFxml, stage);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void otherReadyToTransition() {
    //no use
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @FXML
  public void exit() {
    winSM.exit();

    App.stopApplication();
  }

  @FXML
  public void minimize() {
    winSM.setMinimized();
  }

  @FXML
  public void setFullScreen() {
    if (winSM.isFullScreen()) {
      winSM.setSmall();
    } else {
      winSM.setFullScreen();
    }
  }
}
