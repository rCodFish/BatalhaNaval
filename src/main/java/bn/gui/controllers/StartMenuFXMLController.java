package bn.gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
  private WinStateMachine winAPI = winWrap.getWindowAPI();

  @FXML
  private BorderPane borderPane;

  @FXML
  public void onButtonClick(ActionEvent event)  {
      winAPI.setFullScreen();
  }
  @FXML
  public void onButtonHover(ActionEvent event) {
    Button hoverButton = (Button) event.getSource();
    hoverButton.getStyleClass().add("btn-hover");
  }

  @FXML
  public void onButtonExitHover(ActionEvent event) {
    Button hoverButton = (Button) event.getSource();
    hoverButton.getStyleClass().remove("btn-hover");
  }
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO


  }

}


