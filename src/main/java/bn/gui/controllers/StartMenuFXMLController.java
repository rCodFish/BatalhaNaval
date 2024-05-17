package bn.gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import bn.gui.supportingLogic.windows.WindowAPI;
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
  private WindowAPI winAPI = winWrap.getWindowAPI();

  @FXML
  private BorderPane borderPane;

  @FXML
  public void onButtonClick(ActionEvent event)  {

      winAPI.setFullScreen(borderPane);

  }
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO


  }

}


