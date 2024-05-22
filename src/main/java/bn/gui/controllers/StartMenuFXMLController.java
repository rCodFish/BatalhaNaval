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
import javafx.scene.input.MouseEvent;
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
  private WinStateMachine winAPI = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();
  @FXML
  private ImageView iVimg;
  
  @FXML
  public void onButtonClick(ActionEvent event)  {
      winAPI.setFullScreen();
  }
  
  @FXML
  public void beginGame()  {
      String gameFxml = "/bn/fxml/Game.fxml";
      try{

        winAPI.setFullScreen();
      }catch(Exception e){
        e.printStackTrace();
      }
  }
  
  @FXML
  public void close()  {
      winAPI.exit();
  }
  
  @FXML
  public void onButtonPlayHover(MouseEvent event) {
    Button hoverButton = (Button) event.getSource();
    if(hoverButton.getId().equals("btnX"))
      hoverButton.getStyleClass().add("btnX-hover");
    else
      hoverButton.getStyleClass().add("btnPlay-hover");
  }

  @FXML
  public void onButtonPlayExitHover(MouseEvent event) {
    Button hoverButton = (Button) event.getSource();
    if(hoverButton.getId().equals("btnX"))
      hoverButton.getStyleClass().remove("btnX-hover");
    else
      hoverButton.getStyleClass().remove("btnPlay-hover");

  }
  @FXML
  public void onButtonPlayClick(MouseEvent event) {
    Button hoverButton = (Button) event.getSource();
    if(hoverButton.getId().equals("btnX"))
      hoverButton.getStyleClass().add("btnX-click");
    else
      hoverButton.getStyleClass().add("btnPlay-click");

  }

  @FXML
  public void onButtonPlayExitClick(MouseEvent event) {
    Button hoverButton = (Button) event.getSource();
    if(hoverButton.getId().equals("btnX"))
      hoverButton.getStyleClass().remove("btnX-click");
    else
      hoverButton.getStyleClass().remove("btnPlay-click");
  }
  
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    winAPI.teste(iVimg);
  }

}


