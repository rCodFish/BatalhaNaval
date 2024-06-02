package bn.gameInstance;

import bn.data.Grid.GridCell;
import bn.data.boat.Boat;
import bn.gui.controllers.GameFXMLController;
import bn.gui.controllers.PrepPhaseFXMLController;
import bn.gui.supportingLogic.windows.WindowWrapper;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Eduardo Santos
 */
public class GUIUXController extends UXController {

  private GridCell[][] gridBoxes;
  private ArrayList<Boat> placedBoats = new ArrayList<>();

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

  public void setBoats(ArrayList<Boat> placedBoats) {
    this.placedBoats = new ArrayList<>(placedBoats);
  }
  
  public ArrayList<Boat> getBoats() {
    return placedBoats;
  }

  public void start() throws Exception {

  }

  public void stop() throws Exception {

  }

  public void waitingOtherPartyPrep() {
    System.out.println("[" + new Date() + "] GUIUXController.waitingOtherPartyPrep: start");

    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");

    if (winWrap.getWindowSM().getActiveController() instanceof PrepPhaseFXMLController) {
      PrepPhaseFXMLController controller = (PrepPhaseFXMLController) winWrap.getWindowSM().getActiveController();
      
      System.out.println("I'm "+controller.getClass().getCanonicalName());
    } else if (winWrap.getWindowSM().getActiveController() instanceof GameFXMLController) {
      GameFXMLController controller = (GameFXMLController) winWrap.getWindowSM().getActiveController();
      
      System.out.println("I'm "+controller.getClass().getCanonicalName());
    } else {
      System.out.println("Invalid game state!");
    }

    System.out.println("[" + new Date() + "] GUIUXController.waitingOtherPartyPrep: end");
  }

  public void otherPartyFinishedPrep() {
    System.out.println("[" + new Date() + "] GUIUXController.otherPartyFinishedPrep: start");

    WindowWrapper winWrap = WindowWrapper.getWindowWrapper("first");

    if (winWrap.getWindowSM().getActiveController() instanceof PrepPhaseFXMLController) {
      PrepPhaseFXMLController controller = (PrepPhaseFXMLController) winWrap.getWindowSM().getActiveController();
      
      System.out.println("I'm "+controller.getClass().getCanonicalName());
    } else if (winWrap.getWindowSM().getActiveController() instanceof GameFXMLController) {
      GameFXMLController controller = (GameFXMLController) winWrap.getWindowSM().getActiveController();
      
      System.out.println("I'm "+controller.getClass().getCanonicalName());
    } else {
      System.out.println("Invalid game state!");
    }

    System.out.println("[" + new Date() + "] GUIUXController.otherPartyFinishedPrep: end");
  }

  public void imPlaying() {

  }

  public void otherPlaying() {

  }

  public boolean otherPlayHit(int x, int y) {
    return true;
  }

  public void otherPartyFinishedGame() {

  }

  public void otherPartyIsReadyToPlay() {

  }

}
