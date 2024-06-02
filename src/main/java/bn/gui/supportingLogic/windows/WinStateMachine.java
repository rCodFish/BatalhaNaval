package bn.gui.supportingLogic.windows;

import java.io.IOException;

import javafx.application.Platform;
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

  private double xPercentage = 0.45;
  private double yPercentage = 0.45;

  private double xOffset = 0;
  private double yOffset = 0;

  private double originalX;
  private double originalY;

  private Stage stage;
  private Parent root;

  private T activeController;

  //Initializer///////////////////////////////////////
  public WinStateMachine(Stage stage, int xPer, int yPer) {
    if (xPer > 0 && xPer <= 100 && yPer > 0 && yPer <= 100) {
      xPercentage = (double)xPer * 0.01;
      yPercentage = (double)yPer * 0.01;
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

  //incomplete
  public void toggleFullScreen() {
    Rectangle2D bounds = Screen.getPrimary().getBounds();
    double y = bounds.getHeight();
    double x = bounds.getWidth();

    if (!fullScreen) {
      if (small) {
        originalX = stage.getX();
        originalY = stage.getY();
      }
      stage.setFullScreen(true);
      updateSizeStatus("fullScreen");
    } else {
      stage.setFullScreen(false);
      updateSizeStatus("small");
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

      makeDraggable(false);
      updateSizeStatus("maximized");
    }
  }

  public void setMinimized() {
    if (!minimized) {
      stage.setIconified(true);
      updateSizeStatus("minimized");
    }
  }

  public void setSmall() {
    if (!small) {
      Rectangle2D bounds = Screen.getPrimary().getBounds();
      
      stage.setX(originalX);
      stage.setY(originalY);
      stage.setWidth(bounds.getWidth() * xPercentage);
      stage.setHeight(bounds.getHeight() * yPercentage);

      makeDraggable(true);
      updateSizeStatus("small");
    }
  }

  public void setFullScreen() {
    if (!fullScreen) {
      stage.setFullScreen(true);
      makeDraggable(false);
      updateSizeStatus("fullScreen");
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
    Platform.exit();
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
        fullScreen = false;
        updateSizeStatus("small");
      }
    });
  }

  private void updateSizeStatus(String... states) {
    maximized = false;
    fullScreen = false;
    minimized = false;
    small = false;

    for (String state : states) {
      switch (state) {
        case "maximized":
          maximized = true;
          break;
        case "fullScreen":
          fullScreen = true;
          break;
        case "minimized":
          minimized = true;
          break;
        case "small":
          small = true;
          makeDraggable(true);
          break;
      }
    }
  }
}
