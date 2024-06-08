package bn.gameInstance;

import bn.data.Grid.GridCell;
import bn.data.boat.Boat;
import bn.gui.controllers.GameFXMLController;
import bn.gui.controllers.PrepPhaseFXMLController;
import bn.gui.supportingLogic.windows.WindowWrapper;
import bn.utils.Utils;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Santos
 */
public class GUIUXController extends UXController {

  private boolean imFirstPlaying = false;

  private boolean mySceneTransitionRdy = false;
  private boolean otherSceneTransitionRdy = false;

  private final int MENU_SCREEN = 0;
  private final int PREP_SCREEN = 1;
  private final int GAME_SCREEN = 2;
  private final int ENDING_SCREEN = 3;

  private int currentScene = PREP_SCREEN;

  private GridCell[][] gridBoxes;
  private ArrayList<Boat> placedBoats = new ArrayList<>();

  /**
   *
   * @param gameInstance
   */
  public GUIUXController(GameEngine gameInstance) {
    super(gameInstance);

    gridBoxes = new GridCell[8][8];
    populateGrid();
  }

  private void populateGrid() {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        GridCell gridCell = new GridCell(x, y);
        gridBoxes[x][y] = gridCell;
      }
    }
  }

  /**
   *
   * @param placedBoats
   */
  @Override
  public void setBoats(ArrayList<Boat> placedBoats) {
    this.placedBoats = new ArrayList<>(placedBoats);
    markBoatPositions();
  }

  private void markBoatPositions() {
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        gridBoxes[x][y].setBoat(false);
      }
    }

    for (Boat boat : placedBoats) {
      int[] startingCoordinates = boat.getStartingCoordinates();
      int x = startingCoordinates[0];
      int y = startingCoordinates[1];
      int size = boat.getSize();

      if (boat.isVertical()) {
        for (int i = 0; i < size; i++) {
          gridBoxes[x][y + i].setBoat(true);
        }
      } else {
        for (int i = 0; i < size; i++) {
          gridBoxes[x + i][y].setBoat(true);
        }
      }
    }
  }

  public boolean amIFirstPlaying() {
    return imFirstPlaying;
  }

  /**
   *
   * @return
   */
  @Override
  public ArrayList<Boat> getBoats() {
    return placedBoats;
  }

  //passageTicket true for changing scene and false for warning own gui
  private void sceneSwitchMethods(boolean passageTicket) {
    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");

    switch (currentScene) {
      case MENU_SCREEN:
        break;

      case PREP_SCREEN:
        if (winWrap.getWindowSM().getActiveController() instanceof PrepPhaseFXMLController) {
          PrepPhaseFXMLController controller = (PrepPhaseFXMLController) winWrap.getWindowSM().getActiveController();

          if (passageTicket) {
            controller.transition();
            currentScene = GAME_SCREEN;
          } else {
            controller.otherReadyTotransition();
          }

        } else {
          Utils.errorMessage("scene change from " + currentScene + " to " + GAME_SCREEN);
        }
        break;

      case GAME_SCREEN:

        break;
    }
  }

  /**
   *
   * @throws Exception
   */
  @Override
  public void start() throws Exception {
  }

  /**
   *
   * @throws Exception
   */
  @Override
  public void stop() throws Exception {
  }

  /**
   *
   * @return
   */
  @Override
  public boolean isMySceneTransitionRdy() {
    return mySceneTransitionRdy;
  }

  /**
   *
   * @return
   */
  @Override
  public boolean isOtherSceneTransitionRdy() {
    return otherSceneTransitionRdy;
  }

  /**
   *
   */
  @Override
  public void setMySceneTransitionRdy() {
    mySceneTransitionRdy = true;

    if (otherSceneTransitionRdy) {
      mySceneTransitionRdy = false;
      otherSceneTransitionRdy = false;

      sceneSwitchMethods(true);
    } else {
      imFirstPlaying = true;
    }
  }

  private boolean hasHit_1x1(int x, int y) {
    GridCell gridBox = gridBoxes[x][y];
    if (gridBox.hasBoat() && !gridBox.hasBeenHit()) {
      gridBox.setBeenHit(true);
      return true;
    } else {
      gridBox.setBeenHit(true);
      return false;
    }
  }

//UIUX_Dispatchers/////////////////////////////
  /**
   *
   */
  @Override
  public void otherRdyToTransition() {
    otherSceneTransitionRdy = true;

    if (mySceneTransitionRdy) {
      mySceneTransitionRdy = false;
      otherSceneTransitionRdy = false;

      sceneSwitchMethods(true);
    } else {
      sceneSwitchMethods(false);
    }
  }

  /**
   *
   */
  @Override
  public void imPlaying() {
    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
    if (winWrap.getWindowSM().getActiveController() instanceof GameFXMLController) {
      GameFXMLController controller = (GameFXMLController) winWrap.getWindowSM().getActiveController();
      controller.imPlaying();
    }
  }

  /**
   *
   */
  @Override
  public void otherPlaying() {
    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
    if (winWrap.getWindowSM().getActiveController() instanceof GameFXMLController) {
      GameFXMLController controller = (GameFXMLController) winWrap.getWindowSM().getActiveController();
      controller.otherPlaying();
    }
  }

  @Override
  public void otherHit(int x, int y) {
    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
    if (winWrap.getWindowSM().getActiveController() instanceof GameFXMLController) {
      GameFXMLController controller = (GameFXMLController) winWrap.getWindowSM().getActiveController();
      boolean hit = hasHit_1x1(x, y);
      System.out.println("hit: " + hit);
      controller.otherHit(hit, x, y);
    } else {
      Utils.errorMessage("rather unexpected error ngl");
    }
  }

  public void otherHitResponse(boolean hit) {
    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");
    if (winWrap.getWindowSM().getActiveController() instanceof GameFXMLController) {
      GameFXMLController controller = (GameFXMLController) winWrap.getWindowSM().getActiveController();
      controller.otherHitResponse(hit);
    }
  }
}
