package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public class Boat {
  private int gridInitialX;
  private int gridFinalX;
  private int gridInitialY;
  private int gridFinalY;

  public Boat(int gridInitialX, int gridInitialY, int size, boolean vertical) {
    if (vertical) {
      this.gridInitialX = gridInitialX;
      this.gridFinalX = gridInitialX;
      this.gridInitialY = gridInitialY;
      this.gridFinalY = gridInitialY + size - 1;
    } else {
      this.gridInitialX = gridInitialX;
      this.gridFinalX = gridInitialX + size - 1;
      this.gridInitialY = gridInitialY;
      this.gridFinalY = gridInitialY;
    }
  }

  public int getGridInitialX() {
    return gridInitialX;
  }

  public int getGridFinalX() {
    return gridFinalX;
  }

  public int getGridInitialY() {
    return gridInitialY;
  }

  public int getGridFinalY() {
    return gridFinalY;
  }
}
