package bn.gui.controllers;

import bn.app.App;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos & Luís Amândio
 */
public class EndFXMLController extends GuiBaseController implements Initializable {

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  @FXML
  private Label endLabel;
  @FXML
  private ImageView minImg;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    boolean didIWin = App.gameInstance.getUXController().didIWin();
    String endString = (didIWin == true) ? "You won!" : "You lost!";
    endLabel.setText(endString);
  }

  @Override
  @FXML
  public void transition() {
    exit();
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
      minImg.setImage(new Image(getClass().getResource("/bn/img/maximize.png").toExternalForm()));
    } else {
      winSM.setFullScreen();
      minImg.setImage(new Image(getClass().getResource("/bn/img/minimize.png").toExternalForm()));
    }
  }

  @Override
  public void caughtEsc() {
    if (winSM.isFullScreen()) {
      minImg.setImage(new Image(getClass().getResource("/bn/img/minimize.png").toExternalForm()));
    } else {
      minImg.setImage(new Image(getClass().getResource("/bn/img/maximize.png").toExternalForm()));
    }
  }
}
