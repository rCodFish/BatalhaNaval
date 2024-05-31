package bn.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
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
    String PrepPhaseFXML = "/bn/fxml/PrepPhase.fxml";
    try {
      winAPI.setRoot(PrepPhaseFXML, stage);
      winAPI.setFullScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @FXML
  public void options() {
    String PrepPhaseFXML = "/bn/fxml/Options.fxml";
    try {
      winAPI.showNewStage(PrepPhaseFXML);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void close() {
    winAPI.exit();
  }

  public void minimize() {}

}
