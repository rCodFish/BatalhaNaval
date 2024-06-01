package bn.gui.supportingLogic.windows;

import java.io.IOException;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Eduardo Santos
 */
public class WindowWrapper {

  private static HashMap<String, WindowWrapper> windowWrappers = new HashMap<>();
  private static Object mutex = new Object();
  private static int idCounter = 1;
  private final int id;

  private Window window;
  private WinStateMachine winSM;

  public WindowWrapper(String initialFXML, String hashKey, int xPer, int yPer) {
    synchronized (mutex) {
      this.id = idCounter++;
    }
    createWindow(initialFXML, hashKey, xPer, yPer, true);
  }

  public WindowWrapper(String initialFXML, String hashKey) {
    synchronized (mutex) {
      this.id = idCounter++;
    }
    createWindow(initialFXML, hashKey, 0, 0, false);
  }

  public Window getWindow() {
    return window;
  }

  public WinStateMachine getWindowSM() {
    return winSM;
  }

  public int getID() {
    return id;
  }
  
  public void deleteWinWrap() {
    try {
        windowWrappers.remove(this.id);

        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    } catch (Exception e) {
        System.err.println("Error occurred while trying to delete WindowWrapper: " + e.getMessage());
    }
}

  public Stage getStage() {
    return getStage(window);
  }

  private Stage getStage(Window window) {
    if (window instanceof Stage) {
      return (Stage) window;
    } else {
      throw new IllegalArgumentException("The provided window is not an instance of Stage.");
    }
  }

  public static void addWrapper(String key, WindowWrapper windowWrapper) throws Exception {
    windowWrappers.put(key, windowWrapper);
  }

  public static void removeWrapper(String key) throws Exception {
    windowWrappers.remove(key);
  }

  public static WindowWrapper getWindowWrapper(String key) {
    return windowWrappers.get(key);
  }

  /**
   * Creates a new window with the specified FXML file, width, and height, and
   * adds it to the application.
   *
   * @param initialFXML The FXML file path for the scene.
   * @param initialWidth The initial width of the window.
   * @param initialHeight The initial height of the window.
   * @param hashKey The key for the hashmap.
   */
  private void createWindow(String initialFXML, String hashKey, int xPer, int yPer, boolean useDimensions) {
    try {
      addWrapper(hashKey, this);
    } catch (Exception e) {
      System.err.println("WarningException: " + e.getMessage());
      return;
    }

    Stage stage = new Stage();
    window = stage;

    if (useDimensions) {
      winSM = new WinStateMachine(stage, xPer, yPer);
    } else {
      winSM = new WinStateMachine(stage);
    }

    try {
      winSM.setRoot(initialFXML, stage);
    } catch (IOException e) {
      System.out.println("[Error: loading fxml gone bad]");
    }
  }
}
