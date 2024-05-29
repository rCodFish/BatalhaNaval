package bn.gui.controllers;

import bn.gui.supportingLogic.BoatHBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Eduardo Santos
 */
public class PrepPhaseFXMLController extends BaseController implements Initializable {

  @FXML
  private VBox BoatsVBox;
  @FXML
  private GridPane PrepGrid;

  private ArrayList<BoatHBox> boatOptions = new ArrayList<>();
  private boolean boatSelected = false;
  private boolean gridCellSelected = false;
  private int selectedRow = -1;
  private int selectedColumn = -1;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    addBoatsOptions();
    PrepGrid.setOnMouseClicked(event -> clickGrid(event));;
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
      HBox boatBox = boatOption.getBoatOption();
      
      boatBox.setOnMouseClicked(event -> handleBoatOptionClick(boatOption));
      boatBox.setOnMouseEntered(e -> {
        if (!boatOption.isSelected()) {
          boatOption.getBoatOption().setStyle("-fx-background-color: lightgray;");
        }
      });
      boatBox.setOnMouseExited(e -> {
        if (!boatOption.isSelected()) {
          boatBox.setStyle("-fx-background-color: transparent;");
        }
      });
    }
  }
  
  private void clickGrid(MouseEvent event) {
    Node clickedNode = event.getPickResult().getIntersectedNode();
    if (clickedNode != PrepGrid) {
        // click on descendant node
        Integer colIndex = GridPane.getColumnIndex(clickedNode);
        Integer rowIndex = GridPane.getRowIndex(clickedNode);
        
        if (colIndex != null && rowIndex != null) {
            // Update selected grid cell
            selectedRow = rowIndex;
            selectedColumn = colIndex;
            gridCellSelected = true;
            
            // Change style of the clicked cell
            clickedNode.setStyle("-fx-background-color: lightblue;");
            
            // Do something with the selected grid cell
            System.out.println("Selected grid cell: [" + rowIndex + ", " + colIndex + "]");
        }
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
