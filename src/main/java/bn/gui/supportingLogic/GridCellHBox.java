package bn.gui.supportingLogic;

import javafx.scene.layout.HBox;

/**
 *
 * @author Eduardo Santos
 */
public class GridCellHBox {

  private final HBox cellHBox;
  private boolean isHighlighted = false;
  private final int x;
  private final int y;

  public GridCellHBox(int x, int y) {
    this.x = x;
    this.y = y;
    cellHBox = new HBox();
  }

  public HBox getHBox() {
    return cellHBox;
  }

  public boolean isHighlighted() {
    return isHighlighted;
  }

  public void rmvHighlight() {
    cellHBox.setStyle("-fx-background-color: transparent;");
    isHighlighted = false;
  }

  public void highlight(String color) {
    cellHBox.setStyle("-fx-background-color: " + color + ";");
    isHighlighted = true;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
