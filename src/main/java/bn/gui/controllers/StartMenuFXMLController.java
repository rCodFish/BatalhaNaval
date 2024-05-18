package bn.gui.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
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
  private WinStateMachine winAPI = winWrap.getWindowAPI();
  private Stage stage = winWrap.getStage();

  @FXML
  private BorderPane borderPane;
  @FXML
  private ImageView image;
  @FXML
  private ImageView icon;

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
    
    image.fitWidthProperty().bind(stage.widthProperty());
    //icon.fitWidthProperty().bind(stage.widthProperty());

  }

}


