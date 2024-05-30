
package bn.gui.controllers;

import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos
 */
public class GameFXMLController extends BaseController implements Initializable{
  
  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();
  

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }  
  
}