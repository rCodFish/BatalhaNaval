package bn.gui.controllers;

import bn.app.App;
import bn.data.boat.Boat;
import bn.data.boat.BoatFactory;
import bn.gui.supportingLogic.BoatHBox;
import bn.gui.supportingLogic.GridCellHBox;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
public class PrepPhaseFXMLController extends GuiBaseController implements Initializable {

  public static PrepPhaseFXMLController prepController;

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winAPI = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  private GridCellHBox[][] gridBoxes;
  private ArrayList<BoatHBox> boatOptions = new ArrayList<>();
  private ArrayList<GridCellHBox> highlightedCells = new ArrayList<>();
  private ArrayList<GridCellHBox> boatFilledCells = new ArrayList<>();
  private ArrayList<Boat> placedBoats = new ArrayList<>();

  private boolean isBoatSelected = false;
  private boolean isVerticalPlacement = true;
  private boolean isPlacementValid = false;

  private BoatHBox boatSelected;
  private GridCellHBox currentGridCell;

  private String[][] boatData = {
    {"Carrier", "1"},
    {"Cruiser", "2"},
    {"Destroyer", "2"},
    {"Submarine", "2"}
  };

  @FXML
  private VBox BoatsVBox;
  @FXML
  private GridPane PrepGrid;
  @FXML
  private Label statusLabel;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    prepController = this;
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

        gridCellHB.setOnMouseClicked(e -> cellOnMouseClick());
        gridCellHB.setOnMouseEntered(e -> cellOnMouseEntered(gridCell));
        gridCellHB.setOnMouseExited(e -> cellOnMouseExited(gridCell));

        PrepGrid.add(gridCellHB, x, y);
        gridBoxes[x][y] = gridCell;
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

  private void cellOnMouseClick() {
    if (boatSelected == null) {
      return; // Exit the method early if boatSelected is null
    }

    if (highlightedCells.size() == boatSelected.getSize() && isPlacementValid) {
      if (boatSelected.getCount() > 0) {
        placeBoat();
      }
    }
  }

  private void placeBoat() {
    for (GridCellHBox cell : highlightedCells) {
      cell.select("#383b37");
      boatFilledCells.add(cell);
    }

    try {
      int xIni = currentGridCell.getX();
      int yIni = currentGridCell.getY();
      int xEnd, yEnd;

      if (isVerticalPlacement) {
        xEnd = xIni;
        yEnd = yIni + boatSelected.getSize() - 1;
      } else {
        xEnd = xIni + boatSelected.getSize() - 1;
        yEnd = yIni;
      }
      Boat boat = BoatFactory.createBoat(boatSelected.getType(), xIni, yIni, xEnd, yEnd);
      placedBoats.add(boat);
    } catch (Exception e) {
    }

    highlightedCells.clear();
    isBoatSelected = false;
    boatSelected.decrementCount();
    boatSelected.deselect();
    boatSelected = null;
    isPlacementValid = false;
  }

  private void insertPossibleHighlight(GridCellHBox gridCell) {
    highlightedCells.clear();
    int size = boatSelected.getSize();
    int x = gridCell.getX();
    int y = gridCell.getY();

    if (isVerticalPlacement) {
      //if boats fits in grid && doesnt intercept another boat
      if (y + size <= gridBoxes[x].length && !intersectsAnotherBoat()) {

        //highlight all squares that boat type would fill
        for (int i = 0; i < size; i++) {
          gridBoxes[x][y + i].highlight("#0a3608"); //green
          isPlacementValid = true;

          //add highlighted cells to highlightedCells array if not already there
          if (!highlightedCells.contains(gridBoxes[x][y + i])) {
            highlightedCells.add(gridBoxes[x][y + i]);
          }
        }
        //if boat doesnt fit or intercepts another boat
      } else {
        //highlight all squares that boat type would fill but in red
        for (int i = 0; i < size && y + i < gridBoxes[x].length; i++) {
          gridBoxes[x][y + i].highlight("#360808"); //red      
          isPlacementValid = false;

          //add highlighted cells to highlightedCells array if not already there
          if (!highlightedCells.contains(gridBoxes[x][y + i])) {
            highlightedCells.add(gridBoxes[x][y + i]);
          }
        }
      }
      // Horizontal
    } else {
      //if boats fits in grid && doesnt intercept another boat
      if (x + size <= gridBoxes.length && !intersectsAnotherBoat()) {
        //highlight all squares that boat type would fill
        for (int i = 0; i < size; i++) {
          gridBoxes[x + i][y].highlight("#0a3608"); //green   
          isPlacementValid = true;

          //add highlighted cells to highlightedCells array if not already there
          if (!highlightedCells.contains(gridBoxes[x + i][y])) {
            highlightedCells.add(gridBoxes[x + i][y]);
          }
        }
        //if boat doesn't fit
      } else {
        //highlight all squares that boat type would fill but in red
        for (int i = 0; i < size && x + i < gridBoxes.length; i++) {
          gridBoxes[x + i][y].highlight("#360808"); //red  
          isPlacementValid = false;

          //add highlighted cells to highlightedCells array if not already there
          if (!highlightedCells.contains(gridBoxes[x + i][y])) {
            highlightedCells.add(gridBoxes[x + i][y]);
          }
        }
      }
    }
    //System.out.println("Highlighted Cells:" + highlightedCells);
  }

  private boolean intersectsAnotherBoat() {
    int size = boatSelected.getSize();
    int x = currentGridCell.getX();
    int y = currentGridCell.getY();

    if (isVerticalPlacement) {
      // Check if the boat would fit within the grid
      if (y + size > gridBoxes[x].length) {
        return true;
      }
      // Check each cell the boat would occupy
      for (int i = 0; i < size; i++) {
        GridCellHBox currentCell = gridBoxes[x][y + i];
        if (boatFilledCells.contains(currentCell)) {
          return true;
        }
      }
    } else { // Horizontal
      // Check if the boat would fit within the grid
      if (x + size > gridBoxes.length) {
        return true;
      }
      // Check each cell the boat would occupy
      for (int i = 0; i < size; i++) {
        GridCellHBox currentCell = gridBoxes[x + i][y];
        if (boatFilledCells.contains(currentCell)) {
          return true;
        }
      }
    }
    return false;
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
      boatBox.prefHeightProperty().bind(BoatsVBox.heightProperty().multiply(0.2));

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

  private void handleBoatOptionClick(BoatHBox boatOption) {
    if (isBoatSelected) {
      deselectAllBoatOptions();
    }
    if (!boatOption.isSelected() && boatOption.getCount() > 0) {
      boatOption.select();
      boatSelected = boatOption;
      //System.out.println("Clicked on boat type: " + boatOption.getType());
      isBoatSelected = true;
    }
  }

  private void deselectAllBoatOptions() {
    for (BoatHBox boatOption : boatOptions) {
      boatOption.deselect();
    }
  }

  private boolean areAllBoatsPlaced() {
    int boatNumber = 0;

    for (String[] boatData1 : boatData) {
      boatNumber += Integer.parseInt(boatData1[1]);
    }

    return placedBoats.size() == boatNumber;
  }

  @FXML
  private void goBackPlacement() {
    if (!placedBoats.isEmpty()) {
      Boat boatToRemove = placedBoats.remove(placedBoats.size() - 1);
      if (boatToRemove != null) {
        int[] startCoordinates = boatToRemove.getStartingCoordinates();
        int[] endCoordinates = boatToRemove.getEndingCoordinates();

        for (int x = startCoordinates[0]; x <= endCoordinates[0]; x++) {
          for (int y = startCoordinates[1]; y <= endCoordinates[1]; y++) {
            gridBoxes[x][y].rmvSelect("transparent");
            boatFilledCells.remove(gridBoxes[x][y]);
          }
        }

        // really sketchy
        String boatType = boatToRemove.getClass().getSimpleName();

        for (BoatHBox boatOption : boatOptions) {
          if (boatOption.getType().equals(boatType)) {
            boatOption.incrementCount();
            break;
          }
        }

      } else {
        //System.out.println("No boats to remove.");
      }
    } else {
      //System.out.println("No boats placed yet.");
    }
  }

  @FXML
  private void myPrepFinished() {
    if (areAllBoatsPlaced()) {
      App.gameInstance.getUXController().setBoats(placedBoats);
      App.gameInstance.getUXController().setMySceneTransitionRdy();
      App.gameInstance.getLogicController().send_myPrepFinished();
      statusLabel.setText("Waiting for other party!");

      if (App.gameInstance.getUXController().isOtherSceneTransitionRdy()) {
        transition();
      }
    } else {
      statusLabel.setText("Please place all your boats!");
    }
  }

  @FXML
  public void exit() {
    winAPI.exit();

    App.stopApplication();
  }

  @FXML
  public void minimize() {
    winAPI.setMinimized();
  }

  @FXML
  public void setFullScreen() {
    if (winAPI.isFullScreen()) {
      winAPI.setSmall();
    } else {
      winAPI.setFullScreen();
    }
  }

  @Override
  public void transition() {
    String GamePhaseFXML = "/bn/fxml/Game.fxml";
    try {
      winAPI.setRoot(GamePhaseFXML, stage);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    winAPI.setFullScreen();
  }

  @Override
  public void otherReadyTotransition() {
    statusLabel.setText("Opponent has finished prep!");
  }
}
