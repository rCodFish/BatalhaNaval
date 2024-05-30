package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public class Cruiser extends Boat {

  private static final int SIZE = 3;

  public Cruiser(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }
}
