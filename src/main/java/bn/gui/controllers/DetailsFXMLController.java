package bn.gui.controllers;

import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import bn.utils.Globals;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos
 */
public class DetailsFXMLController extends GuiBaseController implements Initializable {

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("details");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  @FXML
  private TextField myPortField;
  @FXML
  private TextField myIpField;
  @FXML
  private TextField oppPortField; 
  @FXML
  private TextField oppIpField;

  private int myPort;
  private int oppPort;
  private String oppIp;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    System.out.println("Hello???");

  }

  @Override
  public void transition() {
    //no use
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void otherReadyToTransition() {
    //no use
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @FXML
    public void imReady() {
    try {
      myPort = Integer.parseInt(myPortField.getText());
      oppPort = Integer.parseInt(oppPortField.getText());
      oppIp = oppIpField.getText();
      
      WindowWrapper winWrapMain = WindowWrapper.getWindowWrapper("first");
      if (winWrapMain.getWindowSM().getActiveController() instanceof StartMenuFXMLController) {
        StartMenuFXMLController controller = (StartMenuFXMLController) winWrapMain.getWindowSM().getActiveController();
        controller.setConnectionData(myPort, oppPort, oppIp);
        winSM.exit();
      }else{
        Globals.errorMessage("Unexpected game state");
      }
    } catch (NumberFormatException e) {
      Globals.errorMessage("Invalid port number format.");
    }
  }

  @FXML
  public void exit() {
    winSM.exit();
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
