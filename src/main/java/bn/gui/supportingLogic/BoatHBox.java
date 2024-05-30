package bn.gui.supportingLogic;

import bn.data.boat.Boat;
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
        break;
      case "Cruiser":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/cruiser.png"))));
        break;
      case "Destroyer":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/destroyer.png"))));
        break;
      case "Submarine":
        image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bn/img/submarine.png"))));
        break;
      default:
        break;
    }

    Label label = new Label(" (x" + boatCount + ")");
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
    boatOption.setStyle("-fx-background-color: lightblue;");
    isSelected = true;
  }

  public String getType() {
    return type;
  }
}
