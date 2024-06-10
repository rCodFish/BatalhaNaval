package bn.gui.supportingLogic;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Class representing an attack HBox for UI.
 *
 * @author Eduardo Santos & Luís Amândio
 */
public class AttackHBox {

  private final HBox attackOption;
  private boolean isSelected = false;
  private int counter;
  private String type;
  private Label label;

  public AttackHBox(String type, int counter) {
    this.type = type;
    this.counter = counter;
    this.attackOption = new HBox();
    this.attackOption.setAlignment(Pos.CENTER);
    this.attackOption.setSpacing(20);
    
    label = new Label(" (x" + counter + ")");
    label.setStyle("-fx-text-fill: white;");

    attackOption.getChildren().addAll(label);
  }

  public HBox getAttackOption() {
    return attackOption;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void deselect() {
    attackOption.setStyle("-fx-background-color: transparent;");
    isSelected = false;
  }

  public void select() {
    if (counter > 0) {
      attackOption.setStyle("-fx-background-color: lightblue;");
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
}
