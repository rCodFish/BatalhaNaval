package bn.gui.supportingLogic;

import java.util.Objects;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Eduardo Santos
 */
public class BoatHBox {
  private final HBox boatOption;
  private boolean isSelected = false;
  private int counter;

  public BoatHBox(String boatType, int boatCount) {

      this.counter = boatCount;
      this.boatOption = new HBox();
      this.boatOption.setAlignment(Pos.CENTER);
      this.boatOption.setSpacing(20);


      ImageView image = new ImageView();
      switch (boatType){

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
    Label label = (Label) boatOption.getChildren().get(1);
    String labelText = label.getText();
    return labelText.substring(0, labelText.indexOf(" ("));
  }
}
