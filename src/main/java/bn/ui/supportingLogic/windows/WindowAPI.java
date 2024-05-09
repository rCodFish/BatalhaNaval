package bn.ui.supportingLogic.windows;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import bn.ui.controllers.BaseController;

public class WindowAPI<T extends BaseController> {

  private boolean maximized = false;
  private double originalX;
  private double originalY;
  private double originalWidth;
  private double originalHeight;
  private double xOffset = 0; 
  private double yOffset = 0;

  //private static final Image MAXIMIZE_IMAGE_ICON = new Image("ks/images/maximize.png");
  //private static final Image RESTORE_IMAGE_ICON = new Image("ks/images/minimize.png");

  private Stage stage;
  private Parent root;

  private T activeController;

  public WindowAPI(Stage stage) {
    this.stage = stage;
    //stage.getIcons().add(new Image("/ks/images/Logo.png"));
    stage.initStyle(StageStyle.UNDECORATED);
  }

  /**
   * To use in a button to exit the program.
   */
  public void exit() {
    Platform.exit();
  }

  /**
   * To use in a button to minimize the window to the taskbar.
   */
  public void minimizeWindow() {
    stage.setIconified(true);
  }

  /**
   * Used to switch the maximize and restore button icons.
   *
   * @param image The image (imageView) that will have the icon changes.
   * @throws Exception If there is an error loading or setting the image.
   */
  public void maximizeRestoreIconPermutation(ImageView image) throws Exception {
    try {
      //image.setImage(maximized ? RESTORE_IMAGE_ICON : MAXIMIZE_IMAGE_ICON);
    } catch (Exception e) {
      throw new Exception("[Error: image not found/loaded properly | " + (maximized ? "restoreImageIcon" : "maximizeImageIcon"), e);
    }
  }

  public void show(){
    stage.show();
  }
  
  /**
   * To use in a button to maximize the window to "full-Screen". Leaves the
   * taskbar visible.
   */
  public void maximizeWindow() {
    if (!maximized) {
      originalX = stage.getX();
      originalY = stage.getY();
      originalWidth = stage.getWidth();
      originalHeight = stage.getHeight();

      Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
      stage.setX(bounds.getMinX());
      stage.setY(bounds.getMinY());
      stage.setWidth(bounds.getWidth());
      stage.setHeight(bounds.getHeight());

      maximized = true;
    } else {
      stage.setX(originalX);
      stage.setY(originalY);
      stage.setWidth(originalWidth);
      stage.setHeight(originalHeight);

      maximized = false;
    }
  }

  /**
   * Prepare the window for the size and position changes. Sets the new width
   * and height as well as the position of the new scene (centers the window on
   * the screen). Sets the maximized status as false.
   *
   * @param width The widht of the new scene.
   * @param height The height of the new scene.
   */
  private void prepareWindowForSceneChange(double width, double height) {
    maximized = false;
    stage.setHeight(height);
    stage.setWidth(width);
    stage.centerOnScreen();
  }

  public T getActiveController() {
    return activeController;
  }

  /**
   * Is it maximized? \(zO___Oz)/
   *
   * @return {@code true} if the window is maximized, {@code false} otherwise.
   */
  public boolean isMaximized() {
    return maximized;
  }

  /**
   * Is it not maximized? !\(zO___Oz)/
   *
   * @return {@code true} if the window is not maximized, {@code false}
   * otherwise.
   */
  public boolean isNotMaximized() {
    return !maximized;
  }

  /**
   * Make a window root draggable by setting event handlers for mouse pressed
   * and dragged events.
   *
   * @param draggable True if the window(root) should be draggable, false
   * otherwise.
   */
  public void makeDraggable(boolean draggable) {
    Node sceneRoot = stage.getScene().getRoot();

    if (draggable && sceneRoot != null) {
      sceneRoot.setOnMousePressed(event -> {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
      });

      sceneRoot.setOnMouseDragged(event -> {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
      });
    } else {
      if (sceneRoot != null) {
        sceneRoot.setOnMousePressed(null);
        sceneRoot.setOnMouseDragged(null);
      }
    }
  }

  /**
   * Make a JavaFX node draggable by setting event handlers for mouse pressed
   * and dragged events.
   *
   * @param node The JavaFX node to make draggable.
   * @param draggable True if the node should be draggable, false otherwise.
   */
  public void makeNodeDraggable(Node node, boolean draggable) {
    if (node == null) {
      // Once again not feeling like handling any possible error so good luck
      return;
    }

    if (draggable) {
      node.setOnMousePressed(event -> {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
      });

      node.setOnMouseDragged(event -> {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
      });
    } else {
      node.setOnMousePressed(null);
      node.setOnMouseDragged(null);
    }
  }

  /**
   * Set the root scene with the specified FXML file, width, and height.Also
 makes the new scene draggable.
   *
   * @param fxml The FXML file path.
   * @param width The width of the new scene.
   * @param height The height of the new scene.
   * @param window The window (stage) to set the new scene.
   * @throws java.io.IOException
   */
  public void setRoot(String fxml, double width, double height, Window window) throws IOException {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        root = fxmlLoader.load();
        stage = (Stage) window;
        Scene newScene = new Scene(root, width, height);
        stage.setScene(newScene);
        prepareWindowForSceneChange(width, height);
        makeDraggable(true);
        activeController = fxmlLoader.getController();
    } catch (IOException e) {
        System.out.println("[Error: Loading FXML failed]");
    }
  }

}
