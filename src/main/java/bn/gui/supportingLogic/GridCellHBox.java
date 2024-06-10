package bn.gui.supportingLogic;

import javafx.scene.layout.HBox;

/**
 *
 * @author Eduardo Santos & Luís Amândio
 */
public class GridCellHBox {

  private final HBox cellHBox;
  private boolean isHighlighted = false;
  private boolean hasBoat = false;
  private boolean hasBeenHit = false;
  private final int x;
  private final int y;

  public GridCellHBox(int x, int y) {
    this.x = x;
    this.y = y;
    cellHBox = new HBox();
  }
  
  public boolean hasBeenHit(){
    return hasBeenHit;
  }
  
  public HBox getHBox() {
    return cellHBox;
  }

  public boolean isHighlighted() {
    return isHighlighted;
  }

  public void rmvHighlight() {
    if (!hasBoat && !hasBeenHit) {
      cellHBox.setStyle("-fx-background-color: transparent;");
      isHighlighted = false;
    }
  }

  public void highlight(String color) {
    if (!hasBoat && !hasBeenHit) {
      cellHBox.setStyle("-fx-background-color: " + color + ";");
      isHighlighted = true;
    }
  }

  public void select(String color) {
    cellHBox.setStyle("-fx-background-color: " + color + ";");
    hasBoat = true;
  }

  public void hit(String color) {
    if (!hasBeenHit) {
      cellHBox.setStyle("-fx-background-color: " + color + ";");
      hasBeenHit = true;
    }
  }

  public void rmvSelect(String color) {
    cellHBox.setStyle("-fx-background-color: " + color + ";");
    hasBoat = false;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean hasBoat() {
    return hasBoat;
  }

  public void setHasBoat(boolean hasBoat) {
    this.hasBoat = hasBoat;
  }

  @Override
  public String toString() {
    return "[" + x + ", " + y + "]";
  }
}
