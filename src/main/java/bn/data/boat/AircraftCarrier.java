
package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public class AircraftCarrier extends Boat{
  private static final int size = 5;
  private boolean destroyed = false;

  public AircraftCarrier(int gridInitialX, int gridInitialY, boolean vertical) {
    super(gridInitialX, gridInitialY, 4, vertical);
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public void setDestroyed(boolean destroyed) {
    this.destroyed = destroyed;
  }
  
  public static int getSize(){
    return size;
  }
}
