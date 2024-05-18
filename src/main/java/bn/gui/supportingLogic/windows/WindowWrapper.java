package bn.gui.supportingLogic.windows;

import java.io.IOException;
import java.util.HashMap;
import javafx.stage.Stage;
import javafx.stage.Window;
import bn.exceptions.WarningException;

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

  public WindowWrapper(String initialFXML, String hashKey) {
    synchronized (mutex) {
      this.id = idCounter++;
    }
    createWindow(initialFXML, hashKey);
  }

  public Window getWindow() {
    return window;
  }

  public WinStateMachine getWindowAPI() {
    return winSM;
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
  private void createWindow(String initialFXML, String hashKey) {
    
    try{
      addWrapper(hashKey, this);
    }catch(WarningException e){
      System.err.println("WarningException: " + e.getMessage());
      return;
    }
    
    Stage stage = new Stage();
    this.window = stage;
    this.winSM = new WinStateMachine(stage);
    try {
      winSM.setRoot(initialFXML, stage);
    } catch (IOException e) {
      System.out.println("[Error: loading fxml gone bad]");
    }

  }
}
