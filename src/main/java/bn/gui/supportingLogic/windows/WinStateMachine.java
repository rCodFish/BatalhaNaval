package bn.gui.supportingLogic.windows;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import bn.gui.controllers.GuiBaseController;
import javafx.scene.input.KeyCode;

public class WinStateMachine<T extends GuiBaseController> {

  private boolean maximized = false;  //Same as fullScreen but with taskBar still visible
  private boolean fullScreen = false; //fullScreen
  private boolean minimized = false;  //same as iconified
  private boolean small = false;  //small form meaning not fullScreen or Maximized but still visible, this is the position to which every set defaults to
  
  private final int FULLSCREEN = 1;
  private final int MINIMIZED = 2;
  private final int MAXIMIZED = 3;
  private final int SMALL = 4;
  
  private double xPercentage = 0.45;
  private double yPercentage = 0.45;

  private double xOffset = 0;
  private double yOffset = 0;

  private double originalX = 0;
  private double originalY = 0;

  private Stage stage;
  private Parent root;

  private T activeController;

  //Initializer///////////////////////////////////////
  public WinStateMachine(Stage stage, int xPer, int yPer) {
    if (xPer > 0 && xPer <= 100 && yPer > 0 && yPer <= 100) {
      xPercentage = (double) xPer * 0.01;
      yPercentage = (double) yPer * 0.01;
    }

    this.stage = stage;
    stage.initStyle(StageStyle.UNDECORATED);
  }

  public WinStateMachine(Stage stage) {
    this.stage = stage;
    stage.initStyle(StageStyle.UNDECORATED);
  }

  //Toggles///////////////////////////////////////
  //incomplete
  public void toggleMaximized() {
    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    double y = bounds.getHeight();
    double x = bounds.getWidth();

    if (!maximized) {
      originalX = stage.getX();
      originalY = stage.getY();

      stage.setX(bounds.getMinX());
      stage.setY(bounds.getMinY());
      stage.setWidth(bounds.getWidth());
      stage.setHeight(bounds.getHeight());

      maximized = true;
    } else {
      stage.setX(originalX);
      stage.setY(originalY);
      stage.setWidth(bounds.getWidth() * xPercentage);
      stage.setHeight(bounds.getHeight() * yPercentage);

      maximized = false;
    }
  }

  public void toggleFullScreen() {
    if (!fullScreen) {
      setFullScreen();
    } else if (fullScreen) {
      stage.setFullScreen(false);
      setSmall();
    }
  }

  //incomplete
  public void toggleMinimizeWindow() {
    if (small) {
      originalX = stage.getX();
      originalY = stage.getY();
    }
    stage.setIconified(true);
  }

  //Sets///////////////////////////////////////
  public void setMaximized() {
    if (!maximized) {
      Rectangle2D bounds = Screen.getPrimary().getBounds();
      double y = bounds.getHeight();
      double x = bounds.getWidth();

      originalX = stage.getX();
      originalY = stage.getY();

      stage.setX(bounds.getMinX());
      stage.setY(bounds.getMinY());
      stage.setWidth(bounds.getWidth());
      stage.setHeight(bounds.getHeight());

      updateSizeStatus(MAXIMIZED);
    }
  }

  public void setMinimized() {
    if (!minimized) {
      stage.setIconified(true);
      //keep commented, in theory it should be used but i cant catch the event of opening the window after being minimized
      //updateSizeStatus("minimized");
    }
  }

  public void setSmall() {
    if (!small) {
      if(fullScreen){
        stage.setFullScreen(false);
      }
      
      Rectangle2D bounds = Screen.getPrimary().getBounds();

      stage.setX(originalX);
      stage.setY(originalY);
      stage.setWidth(bounds.getWidth() * xPercentage);
      stage.setHeight(bounds.getHeight() * yPercentage);

      updateSizeStatus(SMALL);
    }
  }

  public void setFullScreen() {
    if (!fullScreen) {
      stage.setFullScreen(true);
      updateSizeStatus(FULLSCREEN);
    }
  }

  //Gets///////////////////////////////////////
  public T getActiveController() {
    return activeController;
  }

  public Parent getRoot() {
    return root;
  }

  public Scene getScene() {
    return stage.getScene();
  }

  //Is///////////////////////////////////////
  public boolean isMaximized() {
    return maximized;
  }

  public boolean isSmall() {
    return small;
  }

  public boolean isFullScreen() {
    return fullScreen;
  }

  public boolean isMinimized() {
    return minimized;
  }

  //Others///////////////////////////////////////
  public void exit() {
    stage.close();
  }

  public void disableStage() {
  }

  public void show() {
    stage.show();
  }

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

  public void setRoot(String fxml, Window window) throws IOException {
    Rectangle2D bounds = Screen.getPrimary().getBounds();
    double y = bounds.getHeight();
    double x = bounds.getWidth();

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
      root = fxmlLoader.load();
      stage = (Stage) window;
      Scene newScene = new Scene(root, x * xPercentage, y * yPercentage);
      stage.setScene(newScene);
      setupEscKeyHandler(newScene);
      makeDraggable(true);
      prepareWindowForSceneChange(x * xPercentage, y * yPercentage);
      activeController = fxmlLoader.getController();
      root.layout();
      updateSizeStatus(SMALL);
    } catch (IOException e) {
      System.out.println("[Error: Loading FXML failed]" + e.getMessage());
      //e.printStackTrace();
    }
  }

  public void teste(ImageView... imageViews) {
    Rectangle2D bounds = Screen.getPrimary().getBounds();
    double screenWidth = bounds.getWidth();
    double screenHeight = bounds.getHeight();

    for (ImageView imageView : imageViews) {
      imageView.setFitWidth(screenWidth * xPercentage);
      imageView.setFitHeight(screenHeight * yPercentage);
    }
  }

  //Privates///////////////////////////////////////
  private void prepareWindowForSceneChange(double width, double height) {
    updateSizeStatus();
    stage.setHeight(height);
    stage.setWidth(width);
    stage.centerOnScreen();
  }

  private void setupEscKeyHandler(Scene scene) {
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE && fullScreen) {
        stage.setFullScreen(false);
        updateSizeStatus(SMALL);
      }
    });
  }

  private void updateSizeStatus(int... states) {
    maximized = false;
    fullScreen = false;
    minimized = false;
    small = false;

    for (int state : states) {
      switch (state) {
        case MAXIMIZED:
          maximized = true;
          makeDraggable(false);
          break;
        case FULLSCREEN:
          fullScreen = true;
          makeDraggable(false);
          break;
        case MINIMIZED:
          minimized = true;
          break;
        case SMALL:
          small = true;
          makeDraggable(true);
          break;
      }
    }
  }
}
