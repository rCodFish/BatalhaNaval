package bn.gui.controllers;

import bn.app.App;
import bn.data.boat.Boat;
import bn.gui.supportingLogic.AttackHBox;
import bn.gui.supportingLogic.GridCellHBox;
import bn.gui.supportingLogic.windows.WinStateMachine;
import bn.gui.supportingLogic.windows.WindowWrapper;
import bn.utils.Globals;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo Santos
 */
public class GameFXMLController extends GuiBaseController implements Initializable {

  private WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
  private WinStateMachine winSM = winWrap.getWindowSM();
  private Stage stage = winWrap.getStage();

  private ArrayList<AttackHBox> attackOptions = new ArrayList<>();
  private GridCellHBox[][] playerGridBoxes;
  private GridCellHBox[][] enemyGridBoxes;
  private ArrayList<Boat> placedBoats;

  private boolean isCurrentGridEnemy;
  private boolean isCurrentGridPlayer;
  private boolean isMyRound = false;
  private boolean isAttackSelected = false;
  private boolean didIAttack = false;

  private int roundCounter = 1;
  private int hitCounter = 0;
  private int missCounter = 0;
  private int hitsRemaining = -1;

  private GridCellHBox lastAttackGridCell;
  private GridCellHBox currentGridCell;
  private AttackHBox attackSelected;

  @FXML
  private GridPane EnemyGrid;
  @FXML
  private GridPane PlayerGrid;
  @FXML
  private Button endRoundButton;
  @FXML
  private Label statusLabel;
  @FXML
  private Label roundCounterLabel;
  @FXML
  private Label hitCounterLabel;
  @FXML
  private Label missCounterLabel;
  @FXML
  private Label hitsRemainingLabel;
  @FXML
  private VBox attacksVBox;

  private String[][] attackData = Globals.ATTACK_DATA;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    playerGridBoxes = new GridCellHBox[Globals.GRID_SIZE][Globals.GRID_SIZE];
    enemyGridBoxes = new GridCellHBox[Globals.GRID_SIZE][Globals.GRID_SIZE];

    populateGrid(PlayerGrid, playerGridBoxes, this::playerCellOnMouseClick, this::playerCellOnMouseEntered, this::playerCellOnMouseExited);
    populateGrid(EnemyGrid, enemyGridBoxes, this::enemyCellOnMouseClick, this::enemyCellOnMouseEntered, this::enemyCellOnMouseExited);

    getPlaceBoats();
    addAttacksOptions();
    firstPlayInitializer();
  }

  private void addAttacksOptions() {
    for (String[] attackInfo : attackData) {
      String attackType = attackInfo[0];
      int attackCount = Integer.parseInt(attackInfo[1]);

      AttackHBox attackOption = new AttackHBox(attackType, attackCount);
      attacksVBox.getChildren().add(attackOption.getAttackOption());

      HBox attackBox = attackOption.getAttackOption();
      attackOptions.add(attackOption);

      attackBox.setOnMouseClicked(event -> handleAttackOptionClick(attackOption));
      attackBox.prefHeightProperty().bind(attacksVBox.heightProperty().multiply(0.2));

      attackBox.setOnMouseEntered(e -> {
        if (!attackOption.isSelected()) {
          attackOption.getAttackOption().setStyle("-fx-background-color: lightgray;");
        }
      });

      attackBox.setOnMouseExited(e -> {
        if (!attackOption.isSelected()) {
          attackBox.setStyle("-fx-background-color: transparent;");
        }
      });
    }

    attacksVBox.setAlignment(Pos.CENTER);
    attacksVBox.setSpacing(10);
  }

  private void handleAttackOptionClick(AttackHBox attackOption) {
    if (isAttackSelected) {
      deselectAllAttackOptions();
    }
    if (!attackOption.isSelected() && attackOption.getCount() > 0) {
      attackOption.select();
      attackSelected = attackOption;
      isAttackSelected = true;
    }
  }

  private void deselectAllAttackOptions() {
    for (AttackHBox attackOption : attackOptions) {
      attackOption.deselect();
    }
  }

  public void firstPlayInitializer() {
    if (App.gameInstance.getUXController().amIFirstPlaying()) {
      imPlaying();
    }
  }

  private void populateGrid(GridPane grid, GridCellHBox[][] gridBoxes, Runnable onMouseClick, Consumer<GridCellHBox> onMouseEntered, Consumer<GridCellHBox> onMouseExited) {
    for (int x = 0; x < Globals.GRID_SIZE; x++) {
      for (int y = 0; y < Globals.GRID_SIZE; y++) {
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
    placedBoats = new ArrayList<>(App.gameInstance.getUXController().getBoats());
    for (Boat boat : placedBoats) {
      int[] startingCoordinates = boat.getStartingCoordinates();
      int x = startingCoordinates[0];
      int y = startingCoordinates[1];
      int size = boat.getSize();
      //int color = boat.get

      if (boat.isVertical()) {
        for (int i = 0; i < size; i++) {
          playerGridBoxes[x][y + i].select(Globals.GREY);
        }
      } else {
        for (int i = 0; i < size; i++) {
          playerGridBoxes[x + i][y].select(Globals.GREY);
        }
      }
    }
  }

  private void playerCellOnMouseClick() {
    // Implement the player cell mouse click logic here
  }

  private void playerCellOnMouseEntered(GridCellHBox gridCell) {
    currentGridCell = gridCell;
    isCurrentGridEnemy = false;
    isCurrentGridPlayer = true;

    gridCell.highlight(Globals.BLUE);
  }

  private void playerCellOnMouseExited(GridCellHBox gridCell) {
    currentGridCell = null;
    isCurrentGridEnemy = false;
    isCurrentGridPlayer = false;

    gridCell.rmvHighlight();
  }

  private void enemyCellOnMouseClick() {
    if (!didIAttack) {
      GridCellHBox gridCell = currentGridCell;
      App.gameInstance.getLogicController().send_hitOther(gridCell.getX(), gridCell.getY());
      lastAttackGridCell = gridCell;
      didIAttack = true;
    } else {
      statusLabel.setText("You have already attacked!");
    }
  }

  private void enemyCellOnMouseEntered(GridCellHBox gridCell) {
    currentGridCell = gridCell;
    isCurrentGridEnemy = true;
    isCurrentGridPlayer = false;

    gridCell.highlight(Globals.GREEN);
  }

  private void enemyCellOnMouseExited(GridCellHBox gridCell) {
    currentGridCell = null;
    isCurrentGridEnemy = false;
    isCurrentGridPlayer = false;

    gridCell.rmvHighlight();
  }

  public void imPlaying() {
    App.gameInstance.getLogicController().send_myPlayStarted();
    PlayerGrid.setMouseTransparent(false);
    EnemyGrid.setMouseTransparent(false);
    isMyRound = true;
    didIAttack = false;
    roundCounter++;
    roundCounterLabel.setText(String.valueOf(roundCounter));
    endRoundButton.setText("End Round");
    statusLabel.setText("Attack!");
  }

  public void otherPlaying() {
    PlayerGrid.setMouseTransparent(true);
    EnemyGrid.setMouseTransparent(true);
    isMyRound = false;
    roundCounter++;
    roundCounterLabel.setText(String.valueOf(roundCounter));
    endRoundButton.setText("Other player is playing");
    statusLabel.setText("Other player is playing");
  }

  public void finishGame() {
  }

  @FXML
  public void endRound() {
    if (isMyRound) {
      App.gameInstance.getLogicController().send_myPlayFinished();
      isMyRound = false;
    } else {
      endRoundButton.setText("Other player is playing");
      statusLabel.setText("Other player is playing");
    }
  }

  @FXML
  public void exit() {
    winSM.exit();

    App.stopApplication();
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

  @Override
  public void transition() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void otherReadyToTransition() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void otherHit(boolean hit, int x, int y) {
    GridCellHBox gridCell = playerGridBoxes[x][y];

    if (hit) {
      gridCell.hit(Globals.RED);
    } else {
      gridCell.hit(Globals.BLUE);
    }

    App.gameInstance.getLogicController().send_hitResponse(hit);
  }

  //other instance response to my attack
  public void otherHitResponse(boolean hit) {
    if (hit) {
      lastAttackGridCell.hit(Globals.RED);
      hitCounter++;
      didIAttack = false;
      hitCounterLabel.setText(String.valueOf(hitCounter));
    } else {
      lastAttackGridCell.hit(Globals.BLUE);
      missCounter++;
      missCounterLabel.setText(String.valueOf(missCounter));
    }
  }
}
