package bn.gui.controllers;

import bn.app.App;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
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
  private Accordion accordion;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    System.out.println("Hello???");
    openFirstAccordionPane();
  }
  
  private void openFirstAccordionPane(){
    if (!accordion.getPanes().isEmpty()) {
      TitledPane firstPane = accordion.getPanes().get(0);
      accordion.setExpandedPane(firstPane);
    }
  }
  
  @Override
  public void transition() {
    //no use
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void otherReadyTotransition() {
    //no use
    throw new UnsupportedOperationException("Not supported yet.");
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
