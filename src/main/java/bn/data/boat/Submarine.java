package bn.data.boat;

/**
 *
 * @author Eduardo Santos
 */
public class Submarine extends Boat {

  private static final int SIZE = 2;

  public Submarine(int xIni, int yIni, int xEnd, int yEnd) throws Exception {
    super(SIZE, xIni, yIni, xEnd, yEnd);
  }

  public static int getSIZE() {
    return SIZE;
  }
}
