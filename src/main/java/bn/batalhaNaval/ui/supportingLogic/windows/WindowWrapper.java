package bn.batalhaNaval.ui.supportingLogic.windows;

import java.io.IOException;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.stage.Window;
import bn.batalhaNaval.exceptions.WarningException;

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
  private WindowAPI windowAPI;

  public WindowWrapper() {
    synchronized (mutex) {
      this.id = idCounter++;
    }
    this.window = null;
    this.windowAPI = null;
  }

  public WindowWrapper(Stage stage) {
    synchronized (mutex) {
      this.id = idCounter++;
    }
    this.window = stage;
    this.windowAPI = new WindowAPI(stage);
  }

  public Window getWindow() {
    return window;
  }

  public WindowAPI getWindowAPI() {
    return windowAPI;
  }

  public int getID() {
    return id;
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

  public static void addWrapper(String key, WindowWrapper windowWrapper) throws WarningException {
    /*boolean keyExists = windowWrappers.containsKey(key);

    if (keyExists) {
      throw new WarningException("Key already created/assigned", WindowWrapper.class);
    }*/

    windowWrappers.put(key, windowWrapper);
  }

  public static WindowWrapper getWindowWrapper(String key)  {
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
  public void createWindow(String initialFXML, int initialWidth, int initialHeight, String hashKey) {
    try{
      addWrapper(hashKey, this);
    }catch(WarningException e){
      System.err.println("WarningException: " + e.getMessage());
      return;
    }
    Stage stage = new Stage();
    this.window = stage;
    this.windowAPI = new WindowAPI(stage);
    try {
      windowAPI.setRoot(initialFXML, initialWidth, initialHeight, stage);
    } catch (IOException e) {
      System.out.println("[Error: loading fxml gone bad]");
    }

  }
}
