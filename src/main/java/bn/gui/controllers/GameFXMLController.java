package bn.gui.controllers;

import bn.app.App;
import bn.data.boat.Boat;
import bn.gui.supportingLogic.GridCellHBox;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos
 */
public class GameFXMLController extends GuiBaseController implements Initializable {

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  private GridCellHBox[][] playerGridBoxes;
  private GridCellHBox[][] enemyGridBoxes;
  private ArrayList<Boat> placedBoats;

  private boolean isPlacementValid = false;
  private boolean isCurrentGridEnemy;
  private boolean isCurrentGridPlayer;

  private GridCellHBox currentGridCell;

  @FXML
  private GridPane EnemyGrid;
  @FXML
  private GridPane PlayerGrid;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    playerGridBoxes = new GridCellHBox[8][8];
    enemyGridBoxes = new GridCellHBox[8][8];

    populateGrid(PlayerGrid, playerGridBoxes, this::playerCellOnMouseClick, this::playerCellOnMouseEntered, this::playerCellOnMouseExited);
    populateGrid(EnemyGrid, enemyGridBoxes, this::enemyCellOnMouseClick, this::enemyCellOnMouseEntered, this::enemyCellOnMouseExited);
    
    System.out.println("verticalA");
    getPlaceBoats();
  }

  private void populateGrid(GridPane grid, GridCellHBox[][] gridBoxes, Runnable onMouseClick, Consumer<GridCellHBox> onMouseEntered, Consumer<GridCellHBox> onMouseExited) {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        GridCellHBox gridCell = new GridCellHBox(x, y);
        HBox gridCellHB = gridCell.getHBox();

        gridCellHB.setPrefSize(100, 100);

        gridCellHB.setOnMouseClicked(e -> onMouseClick.run());
        gridCellHB.setOnMouseEntered(e -> onMouseEntered.accept(gridCell));
        gridCellHB.setOnMouseExited(e -> onMouseExited.accept(gridCell));

        grid.add(gridCellHB, x, y);
        gridBoxes[x][y] = gridCell;
      }
    }
  }

  private void getPlaceBoats() {
    System.out.println("verticalB");
    placedBoats = new ArrayList<>(App.gameInstance.getUXController().getBoats());
    System.out.println("PLacedBoats: " + placedBoats);
    for (Boat boat : placedBoats) {
      System.out.println("verticalFor");
      int[] startingCoordinates = boat.getStartingCoordinates();
      int x = startingCoordinates[0];
      int y = startingCoordinates[1];
      int size = boat.getSize();

      if (boat.isVertical()) {
        for (int i = 0; i < size; i++) {
          playerGridBoxes[x][y + i].select("#0d0d17"); 
        }
      } else {
        for (int i = 0; i < size; i++) {
          playerGridBoxes[x + i][y].select("#0d0d17"); 
        }
      }
    }
  }

  private void playerCellOnMouseClick() {
    // Implement the player cell mouse click logic here
  }

  private void playerCellOnMouseEntered(GridCellHBox gridCell) {
    HBox cellHBox = gridCell.getHBox();
    currentGridCell = gridCell;
    isCurrentGridEnemy = false;
    isCurrentGridPlayer = true;
    gridCell.highlight("#0a3608");
  }

  private void playerCellOnMouseExited(GridCellHBox gridCell) {
    HBox cellHBox = gridCell.getHBox();

    currentGridCell = null;
    isCurrentGridEnemy = false;
    isCurrentGridPlayer = false;

    gridCell.rmvHighlight();
  }

  private void enemyCellOnMouseClick() {
    // Implement the enemy cell mouse click logic here
  }

  private void enemyCellOnMouseEntered(GridCellHBox gridCell) {
    HBox cellHBox = gridCell.getHBox();

    currentGridCell = gridCell;
    isCurrentGridEnemy = true;
    isCurrentGridPlayer = false;

    gridCell.highlight("#0a3608");
  }

  private void enemyCellOnMouseExited(GridCellHBox gridCell) {
    HBox cellHBox = gridCell.getHBox();

    currentGridCell = null;
    isCurrentGridEnemy = false;
    isCurrentGridPlayer = false;

    gridCell.rmvHighlight();
  }

  @FXML
  public void exit() {
    winSM.exit();
  }

  @FXML
  public void minimize() {
    winSM.setMinimized();
  }
}
