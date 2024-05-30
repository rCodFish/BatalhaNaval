package bn.gui.controllers;

import bn.gui.supportingLogic.BoatHBox;
import bn.gui.supportingLogic.GridCellHBox;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eduardo Santos
 */
public class PrepPhaseFXMLController extends BaseController implements Initializable {

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winAPI = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  @FXML
  private VBox BoatsVBox;
  @FXML
  private GridPane PrepGrid;

  private String[][] boatData = {
    {"Carrier", "1"},
    {"Cruiser", "3"},
    {"Destroyer", "2"},
    {"Submarine", "2"}
  };
  private static GridCellHBox[][] gridBoxes;
  private ArrayList<BoatHBox> boatOptions = new ArrayList<>();
  private ArrayList<GridCellHBox> highlightedCells = new ArrayList<>();
  private boolean isBoatSelected = false;
  private BoatHBox boatSelected;
  private GridCellHBox currentGridCell;
  private boolean isVerticalPlacement = true;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    gridBoxes = new GridCellHBox[8][8];
    populateGrid();
    addBoatsOptions();
    addKeyEventHandler();
  }

  private void addKeyEventHandler() {
    stage.sceneProperty().addListener((obs, oldScene, newScene) -> {
      if (newScene != null) {
        newScene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
          if (event.getCode() == KeyCode.R) {
            isVerticalPlacement = !isVerticalPlacement;

            if (isBoatSelected && currentGridCell != null) {
              removePossibleHighlight();
              insertPossibleHighlight(currentGridCell);
            }

            event.consume();
          }
        });
      }
    });
  }

  private void populateGrid() {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        GridCellHBox gridCell = new GridCellHBox(x, y);
        HBox gridCellHB = gridCell.getHBox();

        gridCellHB.setPrefSize(100, 100);

        gridCellHB.setOnMouseClicked(e -> cellOnMouseClick(gridCell));
        gridCellHB.setOnMouseEntered(e -> cellOnMouseEntered(gridCell));
        gridCellHB.setOnMouseExited(e -> cellOnMouseExited(gridCell));

        PrepGrid.add(gridCellHB, x, y);
        gridBoxes[x][y] = gridCell;
      }
    }
  }

  private void cellOnRPressed(KeyEvent e) {
    if (e.getCode() == KeyCode.R) {
      isVerticalPlacement = !isVerticalPlacement;
      if (isBoatSelected && currentGridCell != null) {
        removePossibleHighlight();
        insertPossibleHighlight(currentGridCell);
      }
    }
  }

  private void cellOnMouseEntered(GridCellHBox gridCell) {
    HBox cellHBox = gridCell.getHBox();
    currentGridCell = gridCell;
    if (isBoatSelected) {
      insertPossibleHighlight(gridCell);
    }
  }

  private void cellOnMouseExited(GridCellHBox gridCell) {
    HBox cellHBox = gridCell.getHBox();
    currentGridCell = null;
    if (isBoatSelected) {
      removePossibleHighlight();
    }
  }

  private void cellOnMouseClick(GridCellHBox gridCell) {
    /*if (highlightedCells.size() == boatSelected.getSize()) {
      if (boatSelected.getCount() > 0) {
        placeBoat();
      }
    }*/
  }

  private void insertPossibleHighlight(GridCellHBox gridCell) {
    highlightedCells.clear();
    int size = 5;
    int x = gridCell.getX();
    int y = gridCell.getY();

    if (isVerticalPlacement) {
      if (y + size <= gridBoxes[x].length) {
        for (int i = 0; i < size; i++) {
          gridBoxes[x][y + i].highlight("#0a3608"); //green
          if (!highlightedCells.contains(gridBoxes[x][y + i])) {
            highlightedCells.add(gridBoxes[x][y + i]);
          }
        }
      } else {
        for (int i = 0; i < size && y + i < gridBoxes[x].length; i++) {
          gridBoxes[x][y + i].highlight("#360808"); //red
          if (!highlightedCells.contains(gridBoxes[x][y + i])) {
            highlightedCells.add(gridBoxes[x][y + i]);
          }
        }
      }
    } else { // Horizontal
      if (x + size <= gridBoxes.length) {
        for (int i = 0; i < size; i++) {
          gridBoxes[x + i][y].highlight("#0a3608"); //green
          if (!highlightedCells.contains(gridBoxes[x + i][y])) {
            highlightedCells.add(gridBoxes[x + i][y]);
          }
        }
      } else {
        for (int i = 0; i < size && x + i < gridBoxes.length; i++) {
          gridBoxes[x + i][y].highlight("#360808"); //red
          if (!highlightedCells.contains(gridBoxes[x + i][y])) {
            highlightedCells.add(gridBoxes[x + i][y]);
          }
        }
      }
    }
    System.out.println("Highlighted Cells:" + highlightedCells);
  }

  private void removePossibleHighlight() {
    for (GridCellHBox cell : highlightedCells) {
      cell.rmvHighlight();
    }

    highlightedCells.clear();
  }

  private void addBoatsOptions() {
    for (String[] boatInfo : boatData) {
      String boatType = boatInfo[0];
      int boatCount = Integer.parseInt(boatInfo[1]);

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

    BoatsVBox.setAlignment(Pos.CENTER);
    BoatsVBox.setSpacing(50);
  }

  private boolean canPlaceBoat() {
    return false;
  }

  private void handleBoatOptionClick(BoatHBox boatOption) {
    if (isBoatSelected) {
      deselectAllBoatOptions();
    }
    if (!boatOption.isSelected()) {
      boatOption.select();
      boatSelected = boatOption;
      System.out.println("Clicked on boat type: " + boatOption.getType());
      isBoatSelected = true;
    }
  }

  private void deselectAllBoatOptions() {
    for (BoatHBox boatOption : boatOptions) {
      boatOption.deselect();
    }
  }

  private String getBoatSelectedType() {
    if (!isBoatSelected) {
      return null;
    }

    return boatSelected.getType();
  }

}
