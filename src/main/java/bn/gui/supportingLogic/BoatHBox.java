package bn.gui.supportingLogic;

import bn.data.boat.Carrier;
import bn.data.boat.Cruiser;
import bn.data.boat.Destroyer;
import bn.data.boat.Submarine;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Class representing a Boat HBox for UI.
 *
 * Author: Eduardo Santos
 */
public class BoatHBox {

  private final HBox boatOption;
  private boolean isSelected = false;
  private int counter;
  private String type;
  private int size;
  private Label label;
  private String color;
  private final String initialStyleClass;

  public BoatHBox(String type, int boatCount) {
    this.type = type;
    counter = boatCount;
    this.boatOption = new HBox();
    this.boatOption.setAlignment(Pos.CENTER);
    this.boatOption.setSpacing(20);

    ImageView image = new ImageView();
    switch (type) {
      case "Carrier":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/carrier.png"))));
        size = Carrier.getSIZE();
        color = Carrier.getCOLOR();
        initialStyleClass = "bottom_hbox";
        break;
      case "Cruiser":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/cruiser.png"))));
        size = Cruiser.getSIZE();
        color = Cruiser.getCOLOR();
        initialStyleClass = "middle_hbox";
        break;
      case "Destroyer":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/destroyer.png"))));
        size = Destroyer.getSIZE();
        color = Destroyer.getCOLOR();
        initialStyleClass = "middle_hbox";
        break;
      case "Submarine":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/submarine.png"))));
        size = Submarine.getSIZE();
        color = Submarine.getCOLOR();
        initialStyleClass = "top_hbox";
        break;
      default:
        initialStyleClass = "top_hbox";
        break;
    }
    
    image.setPreserveRatio(true);
    //image.setFitHeight(boatOption.getHeight());
    //image.setSmooth(true);
    
    label = new Label(" x" + boatCount + "");
    label.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
    HBox.setMargin(label, new Insets(10));

    boatOption.getChildren().addAll(image, label);
    HBox.setHgrow(image, Priority.ALWAYS);
    boatOption.getStyleClass().add(initialStyleClass);
  }
  
  public void setInitialStyle(){
    boatOption.setStyle(null);
  }

  public String getColor() {
    return color;
  }
  
  public HBox getBoatOption() {
    return boatOption;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void deselect() {
    setInitialStyle();
    isSelected = false;
  }

  public void select() {
    if (counter > 0) {
      boatOption.setStyle("-fx-background-color: lightblue;");
      isSelected = true;
    }
  }

  public String getType() {
    return type;
  }

  public int getCount() {
    return counter;
  }
  
  public void incrementCount() {
    counter++;
    label.setText("x" + counter);
  }

  public void decrementCount() {
    if (counter > 0) {
      counter -= 1;
    }

    if (counter >= 0) {
      label.setText("x" + counter);
    }
  }

  public int getSize() {
    return size;
  }
}
