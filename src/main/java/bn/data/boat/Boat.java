package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public abstract class Boat {

  private String type;
  private final int size;

  public Boat(String type, int size) {
    this.size = size;
    this.type = type;
  }

  public String getType() {
    return type;
  }
}