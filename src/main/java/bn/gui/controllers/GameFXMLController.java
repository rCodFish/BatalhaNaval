
package bn.gui.controllers;

import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos
 */
public class GameFXMLController extends BaseController implements Initializable{
  
  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("game");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();
  
  @FXML
  private GridPane playerGrid;
  @FXML
  private GridPane enemyGrid;
  @FXML
  private AnchorPane root;
  @FXML
  private Button fullScreen;
 
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    playerGrid.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double newWidth = newValue.doubleValue();
        playerGrid.setPrefHeight(newWidth);
        enemyGrid.setPrefHeight(newWidth);
      }
    });

    // Defer the setting of initial dimensions to ensure the layout is complete
    Platform.runLater(() -> {
      playerGrid.setPrefHeight(playerGrid.getWidth());
      enemyGrid.setPrefHeight(enemyGrid.getWidth());
    });
  }  
  
  @FXML
  public void onButtonClick(ActionEvent event)  {
      winSM.setFullScreen();
  }
}