package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public class Carrier extends Boat {

  private static final int SIZE = 5;

  public Carrier(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }
}
