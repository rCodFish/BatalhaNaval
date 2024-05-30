package bn.gui.supportingLogic;

import bn.data.boat.Carrier;
import bn.data.boat.Cruiser;
import bn.data.boat.Destroyer;
import bn.data.boat.Submarine;
import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
        break;
      case "Cruiser":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/cruiser.png"))));
        size = Cruiser.getSIZE();
        break;
      case "Destroyer":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/destroyer.png"))));
        size = Destroyer.getSIZE();
        break;
      case "Submarine":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/submarine.png"))));
        size = Submarine.getSIZE();
        break;
      default:
        break;
    }

    label = new Label(" (x" + boatCount + ")");
    label.setStyle("-fx-text-fill: white;");

    boatOption.getChildren().addAll(image, label);
  }

  public HBox getBoatOption() {
    return boatOption;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void deselect() {
    boatOption.setStyle("-fx-background-color: transparent;");
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
