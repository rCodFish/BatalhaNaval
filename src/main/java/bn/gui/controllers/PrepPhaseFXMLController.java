package bn.gui.controllers;

import bn.gui.supportingLogic.BoatHBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Eduardo Santos
 */
public class PrepPhaseFXMLController extends BaseController implements Initializable {

  @FXML
  private VBox BoatsVBox;

  private ArrayList<BoatHBox> boatOptions = new ArrayList<>();
  private boolean boatSelected = false;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    addBoatsOptions();
  }

  private void addBoatsOptions() {
    String[] boatTypes = {"Carrier", "Battleship", "Cruiser", "Submarine"};
    ArrayList<Integer> boatCounts = new ArrayList<>();
    boatCounts.add(1); //carrier
    boatCounts.add(2); //battleship
    boatCounts.add(3); //cruiser
    boatCounts.add(2); //submarine

    for (int i = 0; i < boatTypes.length; i++) {
      String boatType = boatTypes[i];
      int boatCount = boatCounts.get(i);

      BoatHBox boatOption = new BoatHBox(boatType, boatCount);
      boatOptions.add(boatOption);

      BoatsVBox.getChildren().add(boatOption.getBoatOption());

      boatOption.getBoatOption().setOnMouseClicked(event -> handleBoatOptionClick(boatOption));
      boatOption.getBoatOption().setOnMouseEntered(e -> {
        if (!boatOption.isSelected()) {
          boatOption.getBoatOption().setStyle("-fx-background-color: lightgray;");
        }
      });
      boatOption.getBoatOption().setOnMouseExited(e -> {
        if (!boatOption.isSelected()) {
          boatOption.getBoatOption().setStyle("-fx-background-color: transparent;");
        }
      });
    }
  }

  private void handleBoatOptionClick(BoatHBox boatOption) {
    if (boatSelected){
      deselectAll();
    }
    if (!boatOption.isSelected()) {
      boatOption.select();
      System.out.println("Clicked on boat type: " + boatOption.getType());
      boatSelected = true;
    }
  }

  private void deselectAll() {
    for (BoatHBox boatOption : boatOptions) {
      boatOption.deselect();
    }
  }
}
